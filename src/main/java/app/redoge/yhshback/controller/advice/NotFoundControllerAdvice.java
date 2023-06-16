package app.redoge.yhshback.controller.advice;

import app.redoge.yhshback.dto.ErrorDto;
import app.redoge.yhshback.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotFoundControllerAdvice {
    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto trainingNotFoundException(NotFoundException ex) {
        return new ErrorDto(ex.getMessage(),
                HttpStatus.NOT_FOUND.value());
    }
}
