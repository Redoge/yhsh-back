package app.redoge.yhshback.controller.advice;

import app.redoge.yhshback.dto.ErrorDto;
import app.redoge.yhshback.exception.BadCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BadCredentialsExceptionAdvice {
    @ExceptionHandler(value = {BadCredentialsException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDto trainingNotFoundException(BadCredentialsException ex) {
        return new ErrorDto(ex.getMessage(),
                HttpStatus.FORBIDDEN.value());
    }
}
