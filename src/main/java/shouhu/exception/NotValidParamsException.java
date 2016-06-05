package shouhu.exception;

/**
 * Created by liushanchen on 16/6/5.
 */
public class NotValidParamsException extends Exception {
    public NotValidParamsException(String msg) {
        super("NotValidParamsException: "+msg+" is not valid");
    }

}
