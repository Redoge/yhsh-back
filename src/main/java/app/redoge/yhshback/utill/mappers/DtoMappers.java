package app.redoge.yhshback.utill.mappers;

import app.redoge.yhshback.dto.TrainingIntoWorkoutSaveDto;
import app.redoge.yhshback.dto.WorkoutSaveDto;
import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.entity.Workout;
import app.redoge.yhshback.entity.enums.TrainingMode;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.service.ActivityService;
import app.redoge.yhshback.service.UserService;
import app.redoge.yhshback.utill.validators.DtoValidators;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class DtoMappers {
    private final ActivityService activityService;
    private final UserService userService;
    private final DtoValidators dtoValidators;

    public Workout mapWorkoutSaveDtoToWorkout(WorkoutSaveDto dto) throws BadRequestException, NotFoundException {
        if(!dtoValidators.workoutSaveDtoIsValid(dto)) throw new BadRequestException("WorkoutSaveDto is not valid");
        var user = userService.getUserByUsername(dto.username());
        var trainings = new ArrayList<Training>();
        for (TrainingIntoWorkoutSaveDto trainingIntoWorkoutSaveDto : dto.trainings()) {
            Training training = mapTrainingIntoWorkoutSaveDtoToTraining(trainingIntoWorkoutSaveDto);
            trainings.add(training);
        }

        return Workout
                .builder()
                .startTime(dto.startTime())
                .endTime(dto.endTime())
                .user(user)
                .trainings(trainings)
                .removed(false)
                .build();
    }
    public Training mapTrainingIntoWorkoutSaveDtoToTraining(TrainingIntoWorkoutSaveDto dto) throws NotFoundException, BadRequestException {
        if(!dtoValidators.trainingIntoWorkoutSaveDtoIsValid(dto)) throw new BadRequestException("TrainingIntoWorkoutSaveDto is not valid");

        var activity = activityService.getById(dto.activityId());
        return Training
                .builder()
                .mode(TrainingMode.WORKOUT)
                .startTime(dto.startTime())
                .count(dto.count())
                .activity(activity)
                .removed(false)
                .build();
    }
}
