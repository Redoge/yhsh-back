package app.redoge.yhshback.controller.advice;

import app.redoge.yhshback.dto.ErrorDto;
import app.redoge.yhshback.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadRequestControllerAdvice {
    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto trainingNotFoundException(BadRequestException ex) {
        return new ErrorDto(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
    }
}
