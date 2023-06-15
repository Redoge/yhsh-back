package app.redoge.yhshback.controller.advice;

import app.redoge.yhshback.exception.UserIsExistException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.pojo.ErrorPojo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPojo userNotFoundException(UserNotFoundException ex) {
        return new ErrorPojo(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(value = {UserIsExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPojo userIsExistException(UserIsExistException ex) {
        return new ErrorPojo(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
    }
}
