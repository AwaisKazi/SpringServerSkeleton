package shouhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shouhu.exception.*;
import shouhu.model.User;
import shouhu.service.UserService;

import javax.sql.DataSource;


/**
 * Created by liushanchen on 16/6/4.
 */
@RestController
@RequestMapping(UserController.URL)
public class UserController {
    static final String URL = "/user";
    @Autowired
    public DataSource dataSource;
    private UserService us;

    /**
     * 用户注册
     *
     * @param user 在request body中以json格式传过来的user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public User post(@RequestBody User user) throws NotValidParamsException, UsedPhoneException {
        return  getUserService().create(user);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User get(@RequestBody User user) throws NotValidParamsException, MissingParamsException {
        return getUserService().logIn(user);
    }

    /**
     * 获取用户信息,若缺省UID,则返回所有用户信息
     *
     * @param uid
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Object get(@RequestParam(value = "uid", defaultValue = "-1") long uid) {
        if (uid == -1) {
            return getUserService().findAll();
        } else {
            return getUserService().getUser(uid);

        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public boolean put(@RequestBody User user,@RequestHeader("Authorization") String authorization) throws PasswordTooShortException {
        if(authorization!=null &&user!=null){
            if(!authorization.equals(user.getApi_key())){
                user.setApi_key(authorization);
            }
            return getUserService().update(user);
        }
        return false;
    }

    private UserService getUserService() {
        if (this.us == null) {
            try {
                this.us = new UserService(dataSource);
            } catch (JdbcTemplateNullException e) {
                e.printStackTrace();
            }
        }
        return this.us;

    }
}
