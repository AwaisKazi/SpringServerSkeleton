package shouhu.exception;

/**
 * Created by liushanchen on 16/6/5.
 */
public class NullException extends Exception {
    public NullException(String msg) {
        super("NullException: "+msg+" should not be null.");
    }

}
