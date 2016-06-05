package shouhu.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shouhu.Utils.CommonUtils;
import shouhu.Utils.Constants;
import shouhu.Utils.SecurityUtil;
import shouhu.dao.UserDao;
import shouhu.exception.*;
import shouhu.model.User;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by liushanchen on 16/6/4.
 */
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(Constants.ServiceLogName);

    private UserDao userDao;

    public UserService(DataSource dataSource) throws JdbcTemplateNullException {
        if (dataSource == null) {
            throw new JdbcTemplateNullException();
        }
        this.userDao = new UserDao(dataSource);
    }

    public List<User> findAll() {
        log.info(Constants.Operation_query + ": ALL");

        return userDao.getAllUser();
    }

    /**
     * 通过UID获取用户信息
     *
     * @param id
     * @return
     */
    public User getUser(long id) {
        log.info(Constants.Operation_query + "id :" + id);
        return userDao.getUser(id);
    }

    /**
     * 通过phone获取用户
     *
     * @param phone
     * @return
     */
    public User getUser(String phone) {
        log.info(Constants.Operation_query + "phone :" + phone);
        return userDao.getUser(phone);
    }

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    public User logIn(User user) throws MissingParamsException, NotValidParamsException {
        User dbUser = null;
        if (user != null && user.getPhone() != null && user.getPassword_hash() != null) {
            dbUser = getUser(user.getPhone());
            if (dbUser != null && SecurityUtil.checkPassword(user.getPassword_hash(), dbUser.getPassword_hash())) {
                return dbUser;
            } else {
                throw new NotValidParamsException("PASSWORD OR PHONE");
            }
        } else {
            throw new MissingParamsException(user.toString());
        }
    }


    /**
     * 创建用户
     *
     * @param user
     */
    public User create(User user) throws NotValidParamsException, UsedPhoneException {
        long insertedUser;
        user.setApi_key(SecurityUtil.generateUserApiKey());
        user.setInit_time(CommonUtils.getTimeStamp());
        String hashPSW = SecurityUtil.SpringEncryption(user.getPassword_hash());
        user.setPassword_hash(hashPSW);
        if (checkToBeInsertUser(user)) {
            insertedUser = userDao.simpleInsertUser(user);
            user.setUid(insertedUser);
            log.info(Constants.Operation_Insert + Constants.Status_Success + ":" + insertedUser);
            return user;
        } else {
            throw new NotValidParamsException(user.toString());
        }

    }

    /**
     * 在插入用户前检查其参数是否合格
     *
     * @param user
     * @return
     */
    private boolean checkToBeInsertUser(User user) throws UsedPhoneException {
        if (getUser(user.getPhone()) != null) {
            throw new UsedPhoneException(user.getPhone());
        }
        if (user.getApi_key() != null && user.getInit_time() != null
                && user.getPhone() != null && user.getPassword_hash() != null) {
            return true;
        }
        return false;
    }

    private boolean checkPassword(User user) throws PasswordTooShortException {
        if (user.getPassword_hash().length() < 6) {
            throw new PasswordTooShortException();
        }
        return true;
    }

    /**
     * 更新用户信息
     *
     * @param user
     */
    public boolean update(User user) throws PasswordTooShortException {
        User apiKeyUser = getUserByApiKey(user.getApi_key());
        if (checkPassword(user)) {
            String hashPSW = SecurityUtil.SpringEncryption(user.getPassword_hash());
            user.setPassword_hash(hashPSW);
            if (apiKeyUser != null) {
                return userDao.updateUser(user) > 0;
            }
        }
        return false;
    }

    /**
     * 检查apikey是否有效
     *
     * @param apiKey
     * @return
     */
    public boolean isValidApiKey(String apiKey) {
        return userDao.getUserByApi(apiKey) != null;
    }

    /**
     * 获取用户信息
     *
     * @param apiKey
     * @return
     */
    public User getUserByApiKey(String apiKey) {
        return userDao.getUserByApi(apiKey);
    }


    public void saveAll(List<User> userList) {

    }

    public void updateWithAll(List<User> userList) {

    }
}
