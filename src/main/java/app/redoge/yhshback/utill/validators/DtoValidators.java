package app.redoge.yhshback.utill.validators;

import app.redoge.yhshback.dto.*;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
public class DtoValidators {
    public boolean activitySaveRequestDtoIsValid(ActivitySaveRequestDto dto){
        return isNotEmpty(dto.name())
                && isNotEmpty(dto.username())
                && isNotEmpty(dto.notation());
    }

    public boolean trainingSaveRequestDtoIsValid(TrainingSaveRequestDto dto) {
        return dto.activityId() > 0
                && isNotEmpty(dto.username())
                && dto.count() > 0
                && isNotEmpty(dto.start());
    }

    public boolean registerRequestDtoIsValid(RegisterRequestDto request) {
        return isNotEmpty(request.username())
                && isNotEmpty(request.password())
                && isNotEmpty(request.email());
    }

    public boolean authenticationRequestDtoIsValid(AuthenticationRequestDto request) {
        return isNotEmpty(request.username())
                && isNotEmpty(request.password());
    }

    public boolean userUpdateRequestDtoIsValid(UserUpdateRequestDto userUpdateRequest) {
        return isNotEmpty(userUpdateRequest.username())
                && userUpdateRequest.heightSm()>=0
                && userUpdateRequest.weightKg()>=0;
    }

    public boolean activityUpdateDtoIsValid(ActivityUpdateDto dto){
        return isNotEmpty(dto.id())
                && isNotEmpty(dto.notation())
                && isNotEmpty(dto.name());
    }

    public boolean workoutSaveDtoIsValid(WorkoutSaveDto dto){
        return isNotEmpty(dto.trainings())
                && dto.trainings().stream().allMatch(this::trainingIntoWorkoutSaveDtoIsValid)
                && isNotEmpty(dto.username())
                && isNotEmpty(dto.startTime())
                && isNotEmpty(dto.endTime())
                && isNotEmpty(dto.name());
    }
    public boolean trainingIntoWorkoutSaveDtoIsValid(TrainingIntoWorkoutSaveDto dto){
        return dto.activityId() > 0
                && dto.count() > 0
                && isNotEmpty(dto.startTime());
    }

    public boolean friendshipRequestDtoIsValid(FriendshipRequestDto dto) {
        return isNotEmpty(dto.recipientUsername())
                && isNotEmpty(dto.senderUsername());
    }
}
