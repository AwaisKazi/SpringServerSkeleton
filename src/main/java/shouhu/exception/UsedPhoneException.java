package shouhu.exception;

/**
 * Created by liushanchen on 16/6/5.
 */
public class UsedPhoneException extends Exception{
    public UsedPhoneException() {
        super("phone number is used.");
    }

    public UsedPhoneException(String message) {
        super("phone number:"+message+" is used.");
    }
}
