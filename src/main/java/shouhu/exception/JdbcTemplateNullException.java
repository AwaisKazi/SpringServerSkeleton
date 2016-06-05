package shouhu.exception;

/**
 * Created by liushanchen on 16/6/5.
 */
public class JdbcTemplateNullException extends NullException {
    private static final String name = "JdbcTemplate";

    public JdbcTemplateNullException() {
        super(name);
    }
}
