package shouhu.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by liushanchen on 16/6/4.
 */
public abstract class AngleDao {

    protected DataSource dataSource;

    protected AngleDao(DataSource dataSource) {
        this.dataSource=dataSource;
    }


}
