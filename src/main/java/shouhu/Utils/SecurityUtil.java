package shouhu.Utils;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * Created by liushanchen on 16/6/5.
 */
public class SecurityUtil {

    /**
     * 进行MD5加密
     *
     * @param inputStr
     * @return
     */

    public static String encryption(String inputStr) {
        final String KEY_MD5 = "MD5";
        BigInteger bigInteger = null;
        try {
            MessageDigest md = MessageDigest.getInstance(KEY_MD5);
            byte[] inputData = inputStr.getBytes();
            md.update(inputData);
            bigInteger = new BigInteger(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bigInteger.toString(16);
    }
    /**
     * 用spring StandardPasswordEncoder加密
     * @param input
     * @return
     */
    public static String SpringEncryption(String input){
        if(input==null){
            return null;
        }
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
        return passwordEncoder.encode(input);

    }

    /**
     * 检查密码是否一致
     *
     * @return
     */
    public static boolean checkPassword(String psw, String dbPsw) {
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();

        return passwordEncoder.matches(psw,dbPsw);
    }


    /**
     * 产生一个随机的唯一标识
     *
     * @return
     */
    public static String generateUuid() {
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID;
    }

    /**
     * 产生用户API_KEY
     * 在创建用户时,给每一个用户生成一个唯一的APIkey
     * @return
     */
    public static final String generateUserApiKey() {
        String uniqueID = generateUuid();
        return encryption(uniqueID);
    }



}
