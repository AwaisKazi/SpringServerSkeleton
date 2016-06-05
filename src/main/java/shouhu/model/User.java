package shouhu.model;

/**
 * Created by liushanchen on 16/6/3.
 */

public class User {
    /**
     * user table in db:
     *
     * CREATE TABLE `user` (
     * `uid` int(11) NOT NULL AUTO_INCREMENT,
     * `phone` varchar(45) DEFAULT NULL,
     * `user_name` varchar(45) DEFAULT 'parent',
     * `password_hash` varchar(300) DEFAULT NULL,
     * `head_img_url` varchar(45) DEFAULT NULL,
     * `api_key` varchar(45) NOT NULL,
     * `init_time` varchar(45) NOT NULL,
     * PRIMARY KEY (`uid`),
     * UNIQUE KEY `api_key_UNIQUE` (`api_key`),
     * UNIQUE KEY `phone_UNIQUE` (`phone`)
     * ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
     */
    private long uid;
    private String user_name;
    private String phone;
    private String password_hash;
    private String head_img_url;
    private String api_key;
    private String init_time;


    public User() {
    }

    public User(long uid, String user_name, String phone, String password_hash) {
        this.password_hash = password_hash;
        this.phone = phone;
        this.uid = uid;
        this.user_name = user_name;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getHead_img_url() {
        return head_img_url;
    }

    public void setHead_img_url(String head_img_url) {
        this.head_img_url = head_img_url;
    }

    public String getInit_time() {
        return init_time;
    }

    public void setInit_time(String init_time) {
        this.init_time = init_time;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "api_key='" + api_key + '\'' +
                ", uid=" + uid +
                ", user_name='" + user_name + '\'' +
                ", phone='" + phone + '\'' +
                ", password_hash='" + password_hash + '\'' +
                ", head_img_url='" + head_img_url + '\'' +
                ", init_time='" + init_time + '\'' +
                '}';
    }

    public static class Constants{
        public static final String table_name="user";

    }
}
