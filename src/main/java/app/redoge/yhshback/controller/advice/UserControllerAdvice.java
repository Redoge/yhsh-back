package app.redoge.yhshback.controller.advice;

import app.redoge.yhshback.exception.UserIsExistException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(value = {UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto userNotFoundException(UserNotFoundException ex) {
        return new ErrorDto(ex.getMessage(),
                HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(value = {UserIsExistException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto userIsExistException(UserIsExistException ex) {
        return new ErrorDto(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
    }
    @ExceptionHandler(value = {UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto userIsExistException(UsernameNotFoundException ex) {
        return new ErrorDto(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
    }
}
