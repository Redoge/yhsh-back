package app.redoge.yhshback.utill.mappers;

import app.redoge.yhshback.dto.response.ActivityDto;
import app.redoge.yhshback.dto.response.TrainingDto;
import app.redoge.yhshback.dto.response.UserDto;
import app.redoge.yhshback.dto.response.WorkoutDto;
import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.Workout;
import org.springframework.stereotype.Component;

@Component
public class ResponseDtoMapper {

    public UserDto mapUserToUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isEmailConfirmed(),
                user.getUserRole(),
                user.getSex(),
                user.getWeightKg(),
                user.getHeightSm(),
                user.isEnabled()
        );
    }
    public ActivityDto mapActivityToActivityDto(Activity activity){
        return new ActivityDto(
                activity.getId(),
                activity.getName(),
                activity.getNotation(),
                activity.getCreator().getId(),
                activity.isRemoved()
        );
    }
    public TrainingDto mapTrainingToTrainingDto(Training training){
        return new TrainingDto(
                training.getId(),
                mapActivityToActivityDto(training.getActivity()),
                training.getCount(),
                training.getStartTime(),
                training.isRemoved(),
                training.getMode()
        );
    }
    public WorkoutDto mapWorkoutToWorkoutDto(Workout workout){
        var trainings = workout.getTrainings().stream().map(this::mapTrainingToTrainingDto).toList();
        return new WorkoutDto(
                workout.getId(),
                trainings,
                workout.getUser().getId(),
                workout.getStartTime(),
                workout.getName(),
                workout.getEndTime(),
                workout.isRemoved()
        );
    }

}
