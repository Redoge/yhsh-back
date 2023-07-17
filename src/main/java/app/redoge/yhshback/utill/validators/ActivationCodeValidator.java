package app.redoge.yhshback.utill.validators;

import app.redoge.yhshback.entity.ActivationCode;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Component
public class ActivationCodeValidator {
    public boolean isValid(ActivationCode code){
        return isNotEmpty(code) && isNotEmpty(code.getUser()) && isNotExpired(code.getCreatedTime());
    }

    private boolean isNotExpired(LocalDateTime time){
        var now = LocalDateTime.now();
        return time.isAfter(now.minusHours(24)) && time.isBefore(now);
    }
}
