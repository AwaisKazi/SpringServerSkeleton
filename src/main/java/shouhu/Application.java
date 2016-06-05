package shouhu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by liushanchen on 16/6/2.
 */
@SpringBootApplication

public class Application {

    /**
     * this is the main entry of the whole project
     *
     * @param args
     */

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}