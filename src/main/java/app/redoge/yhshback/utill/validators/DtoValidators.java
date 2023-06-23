package app.redoge.yhshback.utill.validators;

import app.redoge.yhshback.dto.ActivitySaveRequestDto;
import app.redoge.yhshback.dto.AuthenticationRequestDto;
import app.redoge.yhshback.dto.RegisterRequestDto;
import app.redoge.yhshback.dto.TrainingSaveRequestDto;
import app.redoge.yhshback.pojo.UserUpdateRequestPojo;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
public class DtoValidators {
    public boolean activitySaveRequestDtoIsValid(ActivitySaveRequestDto dto){
        return isNotEmpty(dto.name()) &&
                isNotEmpty(dto.username()) &&
                isNotEmpty(dto.notation());
    }

    public boolean trainingSaveRequestDtoIsValid(TrainingSaveRequestDto dto) {
        return dto.activityId() > 0 &&
                isNotEmpty(dto.username()) &&
                dto.count() > 0 &&
                isNotEmpty(dto.start());
    }

    public boolean registerRequestDtoIsValid(RegisterRequestDto request) {
        return isNotEmpty(request.username()) &&
                isNotEmpty(request.password()) &&
                isNotEmpty(request.email());
    }

    public boolean authenticationRequestDtoIsValid(AuthenticationRequestDto request) {
        return isNotEmpty(request.username()) &&
                isNotEmpty(request.password());
    }

    public boolean userUpdateRequestPojoIsValid(UserUpdateRequestPojo userUpdateRequest) {
        return isNotEmpty(userUpdateRequest.username()) &&
                userUpdateRequest.heightSm()>=0 &&
                userUpdateRequest.weightKg()>=0;
    }
}
