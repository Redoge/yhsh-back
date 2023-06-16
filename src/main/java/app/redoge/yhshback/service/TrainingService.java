package app.redoge.yhshback.service;

import app.redoge.yhshback.dto.TrainingSaveRequestDto;
import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.repository.ActivityRepository;
import app.redoge.yhshback.repository.TrainingRepository;
import app.redoge.yhshback.utill.TrainingUtil;
import app.redoge.yhshback.utill.filter.TrainingFilter;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@Transactional
public class TrainingService {
    private static final Logger LOGGER = LogManager.getLogger(TrainingService.class);
    private final TrainingRepository trainingRepository;
    private final ActivityRepository activityRepository;
    private final TrainingFilter trainingFilter;
    private final TrainingUtil trainingUtil;

    public TrainingService(TrainingRepository trainingRepository, ActivityRepository activityRepository, TrainingFilter trainingFilter, TrainingUtil trainingUtil) {
        this.trainingRepository = trainingRepository;
        this.activityRepository = activityRepository;
        this.trainingFilter = trainingFilter;
        this.trainingUtil = trainingUtil;
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

    public List<Training> getAllTrainingByUserId(Long userId) {
        LOGGER.debug("Getting all trainings by user id " + userId);
        return trainingRepository.getTrainingByUserId(userId);
    }

    public List<Training> getEnabledTrainingByUserId(Long userId) {
        LOGGER.debug("Getting all trainings by user id " + userId);
        return trainingRepository.getTrainingByUserId(userId).stream()
                .filter(t -> !t.getActivity().isRemoved())
                .filter(t -> !t.isRemoved())
                .sorted(Comparator.comparingLong(Training::getId))
                .toList();
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

    public Map<LocalDate, List<Training>> groupActivityTrainingByDate(Optional<Activity> activity) {
        return activity.isPresent() ?
                trainingUtil.groupedTrainingByDateMap(trainingFilter.filterNotRemovedTraining(activity.get()
                                .getTrainings())
                        .stream()
                        .sorted(Comparator.comparingLong(Training::getId)
                                .reversed())
                        .collect(toList())) :
                Map.of();
    }


    public Map<LocalDate, List<Training>> groupUserTrainingByDate(long userId) {
        List<Training> trainingList = getEnabledTrainingByUserId(userId);
        return trainingUtil.groupedTrainingByDateMap(trainingList);
    }

    public boolean removeByIdAndActivityId(long trainingId, long activityId) {
        LOGGER.debug("Removing training by training id " + trainingId);
        Training training = getById(trainingId).orElse(null);
        if (isNotEmpty(training) && training.getActivity().getId() == activityId) {
            training.setRemoved(true);
            trainingRepository.save(training);
            return true;
        } else {
            LOGGER.debug("Removing training by training id  " + trainingId + " is failed. TRAINING NOT FOUND");
            return false;
        }
    }

    public Training saveAndAddToActivity(Training training, Activity activity) throws BadRequestException {
        activity.addTraining(training);
        return save(training);
    }

    public List<Training> getAllTraining() {
        return trainingRepository.findAll();
    }

    public Training getTrainingById(long id) throws NotFoundException {
        return trainingRepository.findById(id).orElseThrow(() -> new NotFoundException("Training", id));
    }

    public Training saveByDto(TrainingSaveRequestDto trainingDto) throws NotFoundException, BadRequestException {
        var activity = activityRepository.findByIdAndRemoved(trainingDto.activityId(), false)
                .orElseThrow(() -> new NotFoundException("Activity", trainingDto.activityId()));
        return saveAndAddToActivity(Training.builder()
              .activity(activity)
              .startTime(trainingDto.start())
              .endTime(LocalDateTime.now())
              .count(trainingDto.count())
              .build(), activity);
    }
}
