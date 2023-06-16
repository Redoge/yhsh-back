package app.redoge.yhshback.utill.validators;

import app.redoge.yhshback.dto.ActivitySaveRequestDto;
import app.redoge.yhshback.dto.TrainingSaveRequestDto;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
public class DtoValidators {
    public boolean activitySaveRequestDtoIsValid(ActivitySaveRequestDto dto){
        return isNotEmpty(dto.name()) &&
                isNotEmpty(dto.creatorId()) &&
                isNotEmpty(dto.notation());
    }

    public boolean trainingSaveRequestDtoIsValid(TrainingSaveRequestDto dto) {
        return dto.activityId() > 0 &&
                dto.userId() > 0 &&
                dto.count() > 0 &&
                isNotEmpty(dto.start());
    }
}
