package shouhu.exception;

/**
 * Created by liushanchen on 16/6/5.
 */
public class PasswordTooShortException extends Exception {
    public PasswordTooShortException() {
        super("Password is too short.");
    }
}
