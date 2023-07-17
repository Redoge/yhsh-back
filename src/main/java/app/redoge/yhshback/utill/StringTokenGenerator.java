package app.redoge.yhshback.utill;

import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.RandomStringUtils.*;

@Component
public class StringTokenGenerator {
    public String getToken(int length){
        return randomAlphanumeric(length);
    }
}
