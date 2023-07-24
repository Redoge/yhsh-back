package app.redoge.yhshback.service;

import app.redoge.yhshback.dto.WorkoutSaveDto;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.Workout;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.repository.WorkoutRepository;
import app.redoge.yhshback.service.interfaces.ITrainingService;
import app.redoge.yhshback.service.interfaces.IWorkoutService;
import app.redoge.yhshback.utill.mappers.DtoMappers;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WorkoutService implements IWorkoutService {
    private final WorkoutRepository workoutRepository;
    private final ITrainingService trainingService;
    private final DtoMappers dtoMappers;
    @Override
    public List<Workout> getAllWorkouts(){
        return workoutRepository.findAll();
    }
    @Override
    public Workout getById(Long id) throws NotFoundException {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout", id));
    }
    @Override
    public Workout save(Workout workout){
        return workoutRepository.save(workout);
    }
    @Override
    public Workout saveByDto(WorkoutSaveDto dto) throws BadRequestException, NotFoundException {
        var workout = dtoMappers.mapWorkoutSaveDtoToWorkout(dto);
        var trainings = trainingService.saveAll(workout.getTrainings());
        trainingService.addAllToActivity(new ArrayList<>(trainings));
        workout.setTrainings(trainings);
        return save(workout);
    }
    @Override
    public List<Workout> getAllWorkoutsByUser(User user){
        return workoutRepository.findByUserAndRemoved(user, false);
    }
    @Override
    public List<Workout> getAllWorkoutByUsername(String username){
        return workoutRepository.findAllByUserUsernameAndRemovedAndTrainingsRemoved(username, false, false);
    }

    @Transactional
    @Override
    public boolean removeById(Long id) throws NotFoundException {
        var workout = getById(id);
        workout.setRemoved(true);
        trainingService.removeAllTrainings(workout.getTrainings(), workout.getUser());
        save(workout);
        return true;
    }
    @Override
    public List<Workout> getAllWorkoutByUserId(Long userId) {
        return workoutRepository.findAllByUserIdAndRemovedAndTrainingsRemoved(userId, false, false);
    }
}
