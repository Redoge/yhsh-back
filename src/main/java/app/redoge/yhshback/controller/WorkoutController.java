package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.Page;
import app.redoge.yhshback.dto.WorkoutSaveDto;
import app.redoge.yhshback.dto.response.WorkoutDto;
import app.redoge.yhshback.entity.Workout;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.service.interfaces.IWorkoutService;
import app.redoge.yhshback.utill.mappers.PageMapper;
import app.redoge.yhshback.utill.mappers.ResponseDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.redoge.yhshback.utill.consts.Paths.WORKOUT_PATH;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@RestController
@RequestMapping(WORKOUT_PATH)
@AllArgsConstructor
public class WorkoutController {
    private final IWorkoutService workoutService;
    private final ResponseDtoMapper responseDtoMapper;
    private final PageMapper<WorkoutDto> pageMapper;

    @GetMapping
    public Page<WorkoutDto> getAllWorkout(@RequestParam(value = "userId", required = false) Long userId,
                                          @RequestParam(value = "username", required = false) String username,
                                          Pageable pageable) {
        List<Workout> workout;
        if (isNotEmpty(userId)) {
            workout = workoutService.getAllWorkoutByUserId(userId);
        } else if (isNotEmpty(username)) {
            workout = workoutService.getAllWorkoutByUsername(username);
        } else {
            workout = workoutService.getAllWorkouts();
        }
        var dto = workout.stream().map(responseDtoMapper::mapWorkoutToWorkoutDto).toList();
        return pageMapper.mapToPage(dto, pageable);
    }

    @GetMapping("/{id}")
    public WorkoutDto getWorkoutById(@PathVariable long id) throws NotFoundException {
        var workout = workoutService.getById(id);
        return responseDtoMapper.mapWorkoutToWorkoutDto(workout);
    }

    @PostMapping
    public WorkoutDto save(@RequestBody WorkoutSaveDto workoutSaveDto) throws BadRequestException, NotFoundException {
        var workout = workoutService.saveByDto(workoutSaveDto);
        return responseDtoMapper.mapWorkoutToWorkoutDto(workout);
    }

    @DeleteMapping("/{id}")
    public boolean removeById(@PathVariable long id) throws NotFoundException {
        return workoutService.removeById(id);
    }


}
