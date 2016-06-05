package shouhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import shouhu.dao.UserDao;
import shouhu.model.User;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by liushanchen on 16/6/3.
 */
@RestController
public class TestController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    @Autowired
    public  JdbcTemplate jdbcTemplate;
    @Autowired private DataSource dataSource;

    @RequestMapping(value = "/greeting", method = RequestMethod.GET, produces = "application/json")
    public User greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        User u = new User();
        u.setUid(counter.incrementAndGet());
        u.setUser_name(String.format(template, name));
        return u;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
    public void test() {
        UserDao userDao = new UserDao(dataSource);
        userDao.getAllUser();
    }

}
