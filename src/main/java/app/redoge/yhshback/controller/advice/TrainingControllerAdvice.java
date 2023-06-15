package app.redoge.yhshback.controller.advice;

import app.redoge.yhshback.exception.TrainingNotFoundException;
import app.redoge.yhshback.pojo.ErrorPojo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TrainingControllerAdvice {
    @ExceptionHandler(value = {TrainingNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPojo trainingNotFoundException(TrainingNotFoundException ex) {
        return new ErrorPojo(ex.getMessage(),
                HttpStatus.BAD_REQUEST.value());
    }
}
