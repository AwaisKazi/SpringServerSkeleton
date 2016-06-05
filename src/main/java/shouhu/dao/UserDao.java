package shouhu.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;
import shouhu.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liushanchen on 16/6/4.
 */
public class UserDao extends AngleDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        super(dataSource);
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * 使用jdbcTemplate进行插入
     *
     * @param user
     * @return
     */
    @Transactional
    public long insertUser(User user) {
        String sql = "INSERT INTO " + User.Constants.table_name
                + "(phone, user_name,password) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps =
                        connection.prepareStatement(sql, new String[]{"uid"});
                ps.setObject(1, user.getPhone());
                ps.setString(2, user.getUser_name());
                ps.setString(3, user.getPassword_hash());
                return ps;
            }
        };

        jdbcTemplate.update(psc, keyHolder);
//        return jdbcTemplate.update("INSERT INTO user(phone, user_name,password) VALUES (?,?,?)",
//                new Object[]{user.getPhone(), user.getUser_name(), user.getPassword_hash()});
        return (long) keyHolder.getKey();
    }

    /**
     * 使用SimpleJdbcInsert插入
     *
     * @param user
     * @return
     */
    @Transactional
    public long simpleInsertUser(User user) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName(User.Constants.table_name)
                .usingGeneratedKeyColumns("uid");
        /* (phone, user_name,password_hash,head_img_url,api_key,init_time)*/
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("phone", user.getPhone());
        parameters.put("user_name", user.getUser_name());
        parameters.put("password_hash", user.getPassword_hash());
        parameters.put("head_img_url", user.getHead_img_url());
        parameters.put("api_key", user.getApi_key());
        parameters.put("init_time", user.getInit_time());
        long newId = insertActor.executeAndReturnKey(parameters).longValue();
        user.setUid(newId);
        return newId;
    }

    /**
     * 根据UID查找用户
     *
     * @param id
     * @return
     */
    public User getUser(long id) {
        UserMappingQuery userMappingQuery =
                UserMappingQuery.getUserMappingQuery(dataSource, UserMappingQuery.ByUid);
        return userMappingQuery.findObject(id);
    }

    /**
     * 根据phone查找用户
     *
     * @param phone
     * @return
     */
    public User getUser(String phone) {
        UserMappingQuery userMappingQuery =
                UserMappingQuery.getUserMappingQuery(dataSource, UserMappingQuery.ByPhone);
        return userMappingQuery.findObject(phone);
    }

    /**
     * 根据phone查找用户
     *
     * @param apiKey
     * @return
     */
    public User getUserByApi(String apiKey) {
        UserMappingQuery userMappingQuery =
                UserMappingQuery.getUserMappingQuery(dataSource, UserMappingQuery.ByApiKey);
        return userMappingQuery.findObject(apiKey);
    }

    public static class UserMappingQuery extends MappingSqlQuery<User> {
        private static final String queryIdSql = "SELECT * FROM user WHERE uid = ?";
        private static final String queryPhoneSql = "SELECT * FROM user WHERE phone = ?";
        private static final String queryApiKeySql = "SELECT * FROM user WHERE api_key = ?";
        private static final String querySql = "SELECT * FROM user";
        private static final int ByUid = 1;
        private static final int ByPhone = 2;
        private static final int ByApiKey = 3;
        private static final int All = 0;

        private UserMappingQuery(DataSource ds, String sql, int type) {
            super(ds, sql);
            super.declareParameter(new SqlParameter("uid", type));
            compile();
        }

        private UserMappingQuery(DataSource ds, String sql) {
            super(ds, sql);
            compile();
        }

        public static UserMappingQuery getUserMappingQuery(DataSource ds, int how) {
            switch (how) {
                case All:
                    return new UserMappingQuery(ds, querySql);
                case ByUid:
                    return new UserMappingQuery(ds, queryIdSql, Types.INTEGER);
                case ByPhone:
                    return new UserMappingQuery(ds, queryPhoneSql, Types.VARCHAR);
                case ByApiKey:
                    return new UserMappingQuery(ds, queryApiKeySql, Types.VARCHAR);
                default:
                    throw new IllegalArgumentException("int how:" + how);
            }
        }

        @Override
        protected User mapRow(ResultSet rs, int rowNumber) throws SQLException {
            User user = new User();
            user.setUid(rs.getLong("uid"));
            user.setPhone(rs.getString("phone"));
            user.setUser_name(rs.getString("user_name"));
            user.setPassword_hash(rs.getString("password_hash"));
            user.setHead_img_url(rs.getString("head_img_url"));
            user.setApi_key(rs.getString("api_key"));
            user.setInit_time(rs.getString("init_time"));
            return user;
        }
    }

    public List<User> getAllUser() {
//        jdbcTemplate.query(
//                "SELECT * FROM user",
//                (rs, rowNum) -> new User(rs.getLong("uid"), rs.getString("user_name"), rs.getString("phone"), rs.getString("password"))
//        ).forEach(customer -> log.info(customer.toString()));
        UserMappingQuery userMappingQuery =
                UserMappingQuery.getUserMappingQuery(dataSource, UserMappingQuery.All);
        return userMappingQuery.execute();
    }

    private final String updateSql = "UPDATE user SET " +
            "phone = :phone ," +
            "user_name = :user_name , " +
            "password_hash = :password_hash , " +
            "head_img_url = :head_img_url  " +
            "where api_key = :api_key";

    public int updateUser(User user) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(user);
        int updateCounts = namedParameterJdbcTemplate.update(updateSql, sqlParameterSource);
        return updateCounts;
    }

}
