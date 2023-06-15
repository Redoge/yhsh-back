package app.redoge.yhshback.controller;

import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.exception.TrainingNotFoundException;
import app.redoge.yhshback.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static app.redoge.yhshback.utill.paths.Constants.TRAININGS_PATH;


@RestController
@AllArgsConstructor
@RequestMapping(TRAININGS_PATH)
public class TrainingController {
    private final TrainingService trainingService;

    @GetMapping
    public List<Training> getAllTrainings() {
        return trainingService.getAllTraining();
    }
    @GetMapping("/{id}")
    public Training getTrainingById(@PathVariable long id) throws TrainingNotFoundException {
        return trainingService.getTrainingById(id);
    }
}
