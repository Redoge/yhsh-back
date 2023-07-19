package app.redoge.yhshback.service;

import app.redoge.yhshback.dto.WorkoutSaveDto;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.Workout;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.repository.WorkoutRepository;
import app.redoge.yhshback.utill.mappers.DtoMappers;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final UserService userService;
    private final TrainingService trainingService;
    private final DtoMappers dtoMappers;
    public List<Workout> getAllWorkouts(){
        return workoutRepository.findAll();
    }

    public Workout getById(Long id) throws NotFoundException {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workout", id));
    }

    public Workout save(Workout workout){
        return workoutRepository.save(workout);
    }

    public Workout saveByDto(WorkoutSaveDto dto) throws BadRequestException, NotFoundException {
        var workout = dtoMappers.mapWorkoutSaveDtoToWorkout(dto);
        return save(workout);
    }

    public List<Workout> getAllWorkoutsByUser(User user){
        return workoutRepository.findByUserAndRemoved(user, false);
    }
    public List<Workout> getAllWorkoutByUsername(String username){
        return workoutRepository.findAllByUserUsernameAndRemovedAndTrainingsRemoved(username, false, false);
    }

    @Transactional
    public boolean removeById(Long id) throws NotFoundException {
        var workout = getById(id);
        workout.setRemoved(true);
        trainingService.removeAllTrainings(workout.getTrainings(), workout.getUser());
        save(workout);
        return true;
    }

    public List<Workout> getAllWorkoutByUserId(Long userId) {
        return workoutRepository.findAllByUserIdAndRemovedAndTrainingsRemoved(userId, false, false);
    }
}
