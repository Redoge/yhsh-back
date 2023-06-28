package app.redoge.yhshback.service;

import app.redoge.yhshback.dto.TrainingSaveRequestDto;
import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.repository.ActivityRepository;
import app.redoge.yhshback.repository.TrainingRepository;

import app.redoge.yhshback.utill.validators.DtoValidators;
import jakarta.transaction.Transactional;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@Transactional
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final ActivityRepository activityRepository;
    private final DtoValidators dtoValidators;

    public TrainingService(TrainingRepository trainingRepository, ActivityRepository activityRepository, DtoValidators dtoValidators) {
        this.trainingRepository = trainingRepository;
        this.activityRepository = activityRepository;
        this.dtoValidators = dtoValidators;
    }

    public Training save(Training training) throws BadRequestException {
        if (training.getCount() > 0 &&
                isNotEmpty(training.getActivity()) && isNotEmpty(training.getStartTime())) {
            return trainingRepository.save(training);
        } else {
            throw new BadRequestException("Training not saved!!!");
        }
    }


    @PostAuthorize("returnObject.activity.creator.username.equalsIgnoreCase(authentication.name) or hasRole('ADMIN')")
    public Training getById(long trainingId) throws NotFoundException {
        return trainingRepository.findById(trainingId).orElseThrow(()->new NotFoundException("Training", trainingId));
    }
    @PreAuthorize("@trainingService.getById(#trainingId).activity.creator.username.equalsIgnoreCase(authentication.name) or hasRole('ADMIN')")
    public boolean removeById(long trainingId) throws NotFoundException {
        Training training = getById(trainingId);
        training.setRemoved(true);
        trainingRepository.save(training);
        return true;
    }
    @PreAuthorize("#activity.creator.username.equalsIgnoreCase(authentication.name) or hasRole('ADMIN')")
    public Training saveAndAddToActivity(Training training, Activity activity) throws BadRequestException {
        activity.addTraining(training);
        return save(training);
    }
    @PostAuthorize("returnObject.activity.creator.username.equalsIgnoreCase(authentication.name) or hasRole('ADMIN')")
    public Training getTrainingById(long id) throws NotFoundException {
        return trainingRepository.findByIdAndRemovedAndActivityRemoved(id, false, false)
                .orElseThrow(() -> new NotFoundException("Training", id));
    }

    @PreAuthorize("@activityService.getById(#trainingDto.activityId()).creator.username.equalsIgnoreCase(authentication.name)")
    public Training saveByDto(TrainingSaveRequestDto trainingDto) throws NotFoundException, BadRequestException {
        if(!dtoValidators.trainingSaveRequestDtoIsValid(trainingDto))
            throw new BadRequestException("Training not saved!!!");
        var activity = activityRepository.findByIdAndRemoved(trainingDto.activityId(), false)
                .orElseThrow(() -> new NotFoundException("Activity", trainingDto.activityId()));
        return saveAndAddToActivity(Training.builder()
              .activity(activity)
              .startTime(trainingDto.start())
              .endTime(LocalDateTime.now())
              .count(trainingDto.count())
              .build(), activity);
    }
    @PreAuthorize("#username.equalsIgnoreCase(authentication.name) or hasRole('ADMIN')")
    public List<Training> getAllTrainingByUserUsername(String username) {
        return trainingRepository.getTrainingByActivityCreatorUsernameAndRemovedAndActivityRemoved(username, false, false)
                .stream().sorted(Comparator.comparing(Training::getStartTime).reversed()).toList();
    }
    @PreAuthorize("@userService.getUserById(#userId).username == authentication.name or hasRole('ADMIN')")
    public List<Training> getAllTrainingByUserId(Long userId) {
        return trainingRepository.getTrainingByActivityCreatorIdAndRemovedAndActivityRemoved(userId, false, false)
                .stream().sorted(Comparator.comparing(Training::getStartTime).reversed()).toList();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<Training> getAllTraining() {
        return trainingRepository.findAllByRemovedAndActivityRemoved(false, false)
                .stream().sorted(Comparator.comparing(Training::getStartTime).reversed()).toList();
    }
}
