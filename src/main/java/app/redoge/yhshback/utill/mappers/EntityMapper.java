package app.redoge.yhshback.utill.mappers;

import app.redoge.yhshback.entity.UserWeight;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class EntityMapper {
    public UserWeight mapFloatToUserWeight(Float weight){
        return UserWeight.builder()
                .date(new Date(System.currentTimeMillis()))
                .weight(weight)
                .build();
    }
}
