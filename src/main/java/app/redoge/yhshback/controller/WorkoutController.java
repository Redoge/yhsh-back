package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.WorkoutSaveDto;
import app.redoge.yhshback.entity.Workout;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.service.interfaces.IWorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.redoge.yhshback.utill.consts.Paths.WORKOUT_PATH;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@RestController
@RequestMapping(WORKOUT_PATH)
@AllArgsConstructor
public class WorkoutController {
    private final IWorkoutService workoutService;

    @GetMapping
    public List<Workout> getAllWorkout(@RequestParam(value = "userId", required = false) Long userId,
                                       @RequestParam(value = "username", required = false) String username) {
        if (isNotEmpty(userId))
            return workoutService.getAllWorkoutByUserId(userId);
        if (isNotEmpty(username))
            return workoutService.getAllWorkoutByUsername(username);
        return workoutService.getAllWorkouts();
    }

    @GetMapping("/{id}")
    public Workout getWorkoutById(@PathVariable long id) throws NotFoundException {
        return workoutService.getById(id);
    }

    @PostMapping
    public Workout save(@RequestBody WorkoutSaveDto workoutSaveDto) throws BadRequestException, NotFoundException {
        return workoutService.saveByDto(workoutSaveDto);
    }

    @DeleteMapping("/{id}")
    public boolean removeById(@PathVariable long id) throws NotFoundException {
        return workoutService.removeById(id);
    }


}
