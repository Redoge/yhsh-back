package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.TrainingSaveRequestDto;
import app.redoge.yhshback.dto.response.TrainingDto;
import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.service.interfaces.ITrainingService;
import app.redoge.yhshback.utill.mappers.ResponseDtoMapper;
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
    private final ResponseDtoMapper responseDtoMapper;


    @GetMapping
    public List<TrainingDto> getAllTrainings(@RequestParam(value = "userId", required = false) Long userId,
                                             @RequestParam(value = "username", required = false) String username,
                                             @RequestParam(value = "activityId", required = false) Long activityId) {
        List<Training> trainings;
        if (isNotEmpty(userId)) {
            trainings = trainingService.getAllTrainingByUserId(userId);
        } else if (isNotEmpty(username)) {
            trainings = trainingService.getAllTrainingByUserUsername(username);
        } else if (isNotEmpty(activityId)){
            trainings = trainingService.getAllTrainingByActivityId(activityId);
        } else{
            trainings = trainingService.getAllTraining();
        }
        return trainings.stream().map(responseDtoMapper::mapTrainingToTrainingDto).toList();
    }

    @GetMapping("/{id}")
    public TrainingDto getTrainingById(@PathVariable long id) throws NotFoundException {
        var training =  trainingService.getTrainingById(id);
        return responseDtoMapper.mapTrainingToTrainingDto(training);
    }
    @PostMapping
    public TrainingDto save(@RequestBody TrainingSaveRequestDto trainingDto) throws BadRequestException, NotFoundException {
        var training =  trainingService.saveByDto(trainingDto);
        return responseDtoMapper.mapTrainingToTrainingDto(training);
    }
    @DeleteMapping("/{id}")
    public boolean removeById(@PathVariable long id) throws NotFoundException {
        return trainingService.removeById(id);
    }
}
