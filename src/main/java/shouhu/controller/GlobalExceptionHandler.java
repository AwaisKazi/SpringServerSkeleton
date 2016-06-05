package shouhu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import shouhu.Utils.Constants;
import shouhu.exception.MissingParamsException;
import shouhu.exception.NotValidParamsException;
import shouhu.exception.NullException;
import shouhu.exception.PasswordTooShortException;

import java.sql.SQLException;

/**
 * Created by liushanchen on 16/6/5.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(Constants.ExeptionLogName);


    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(NullException.class)
    @ResponseBody
    public String handleConflict(NullException e) {
        log.info(e.getMessage());
        return e.getMessage();

    }

    /**
     * 处理参数缺失异常
     * @param e
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(PasswordTooShortException.class)
    @ResponseBody
    public String handlePasswordTooShortException(PasswordTooShortException e) {
        log.info(e.getMessage());
        return e.getMessage();

    }
 /**
     * 处理参数缺失异常
     * @param e
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(MissingParamsException.class)
    @ResponseBody
    public String handleMissingParamsExeption(MissingParamsException e) {
        log.info(e.getMessage());
        return e.getMessage();

    }

    /**
     * 处理参数不正确异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(NotValidParamsException.class)
    @ResponseBody
    public String handleNotValidParamsException(NotValidParamsException e) {
        log.info(e.getMessage());
        return e.getMessage();
    }

    /**
     * 处理数据库异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public String handleSqlException(Exception e) {
        log.info(e.getMessage());
        return e.getMessage();
    }

    /**
     * 处理其他异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleOtherException(Exception e) {
        e.printStackTrace();

        log.info(e.getMessage());
        return e.getMessage();
    }
}
