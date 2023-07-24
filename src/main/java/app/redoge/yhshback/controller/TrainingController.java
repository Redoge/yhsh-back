package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.TrainingSaveRequestDto;
import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.service.interfaces.ITrainingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.redoge.yhshback.utill.consts.Paths.TRAININGS_PATH;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


@RestController
@AllArgsConstructor
@RequestMapping(TRAININGS_PATH)
public class TrainingController {
    private final ITrainingService trainingService;

    @GetMapping
    public List<Training> getAllTrainings(@RequestParam(value = "userId", required = false) Long userId,
                                          @RequestParam(value = "username", required = false) String username) {
        if (isNotEmpty(userId))
            return trainingService.getAllTrainingByUserId(userId);
        if (isNotEmpty(username))
            return trainingService.getAllTrainingByUserUsername(username);
        return trainingService.getAllTraining();
    }

    @GetMapping("/{id}")
    public Training getTrainingById(@PathVariable long id) throws NotFoundException {
        return trainingService.getTrainingById(id);
    }
    @PostMapping
    public Training save(@RequestBody TrainingSaveRequestDto trainingDto) throws BadRequestException, NotFoundException {
        return trainingService.saveByDto(trainingDto);
    }
    @DeleteMapping("/{id}")
    public boolean removeById(@PathVariable long id) throws NotFoundException {
        return trainingService.removeById(id);
    }
}
