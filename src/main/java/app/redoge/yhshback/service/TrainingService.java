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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@Transactional
public class TrainingService {
    private static final Logger LOGGER = LogManager.getLogger(TrainingService.class);
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
            LOGGER.info("Saved training: " + training.getActivity().getName());
            return trainingRepository.save(training);
        } else {
            LOGGER.error("Error to saving training");
            throw new BadRequestException("Training not saved!!!");
        }
    }



    public Optional<Training> getById(long trainingId) {
        LOGGER.debug("Getting training by training id " + trainingId);
        return trainingRepository.findById(trainingId);
    }

    public boolean removeById(long trainingId) throws NotFoundException {
        LOGGER.debug("Removing training by training id " + trainingId);
        Training training = getById(trainingId).orElseThrow(() -> new NotFoundException("Training", trainingId));
        training.setRemoved(true);
        trainingRepository.save(training);
        return true;
    }

    public Training saveAndAddToActivity(Training training, Activity activity) throws BadRequestException {
        activity.addTraining(training);
        return save(training);
    }



    public Training getTrainingById(long id) throws NotFoundException {
        return trainingRepository.findByIdAndRemoved(id, false)
                .orElseThrow(() -> new NotFoundException("Training", id));
    }

    public Training saveByDto(TrainingSaveRequestDto trainingDto) throws NotFoundException, BadRequestException {
        if(!dtoValidators.trainingSaveRequestDtoIsValid(trainingDto))
            throw new BadRequestException("Training not saved!!!");
//        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        var username = userDetails.getUsername();
//        if(isNotEmpty(username) && !trainingDto.username().equalsIgnoreCase(username)) throw new UsernameNotFoundException(username);//TODO: check user
        var activity = activityRepository.findByIdAndRemoved(trainingDto.activityId(), false)
                .orElseThrow(() -> new NotFoundException("Activity", trainingDto.activityId()));
        return saveAndAddToActivity(Training.builder()
              .activity(activity)
              .startTime(trainingDto.start())
              .endTime(LocalDateTime.now())
              .count(trainingDto.count())
              .build(), activity);
    }

    public List<Training> getAllTrainingByUserUsername(String username) {
        return trainingRepository.getTrainingByActivityCreatorUsernameAndRemovedAndActivityRemoved(username, false, false)
                .stream().sorted(Comparator.comparing(Training::getStartTime).reversed()).toList();
    }
    public List<Training> getAllTrainingByUserId(Long userId) {
        LOGGER.debug("Getting all trainings by user id " + userId);
        return trainingRepository.getTrainingByActivityCreatorIdAndRemovedAndActivityRemoved(userId, false, false)
                .stream().sorted(Comparator.comparing(Training::getStartTime).reversed()).toList();
    }
    public List<Training> getAllTraining() {
        return trainingRepository.findAllByRemovedAndActivityRemoved(false, false)
                .stream().sorted(Comparator.comparing(Training::getStartTime).reversed()).toList();
    }
}
