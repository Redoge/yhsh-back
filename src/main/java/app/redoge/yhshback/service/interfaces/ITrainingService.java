package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.dto.TrainingSaveRequestDto;
import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;

import java.util.List;


public interface ITrainingService {
    Training save(Training training) throws BadRequestException;

    boolean removeById(long trainingId) throws NotFoundException;
    Training saveAndAddToActivity(Training training) throws BadRequestException;
    Training getTrainingById(long id) throws NotFoundException;

    Training saveByDto(TrainingSaveRequestDto trainingDto) throws NotFoundException, BadRequestException;
    List<Training> getAllTrainingByUserUsername(String username);

    List<Training> getAllTrainingByUserId(Long userId);

    List<Training> getAllTraining();

    void removeAllTrainings(List<Training> trainingList, User user);

    List<Training> addAllToActivity(List<Training> trainings);
    List<Training> saveAll(List<Training> trainings);

}
