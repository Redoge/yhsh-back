package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.dto.WorkoutSaveDto;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.Workout;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;

import java.util.List;

public interface IWorkoutService {
    List<Workout> getAllWorkouts();

    Workout getById(Long id) throws NotFoundException;

    Workout save(Workout workout);

    Workout saveByDto(WorkoutSaveDto dto) throws BadRequestException, NotFoundException;

    List<Workout> getAllWorkoutsByUser(User user);

    List<Workout> getAllWorkoutByUsername(String username);

    boolean removeById(Long id) throws NotFoundException;

    List<Workout> getAllWorkoutByUserId(Long userId);
}
