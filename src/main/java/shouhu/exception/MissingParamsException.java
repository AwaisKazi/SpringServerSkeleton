package shouhu.exception;

/**
 * Created by liushanchen on 16/6/5.
 */
public class MissingParamsException extends Exception {
    public MissingParamsException(String msg) {
        super("MissingParamsException: "+msg+" is missing.");
    }

}
