package app.redoge.yhshback.service;

import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.repository.TrainingRepository;
import app.redoge.yhshback.utill.TrainingUtil;
import app.redoge.yhshback.utill.filter.TrainingFilter;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class TrainingService {
    private static final Logger LOGGER = LogManager.getLogger(TrainingService.class);
    private final TrainingRepository trainingRepository;
    private final TrainingFilter trainingFilter;
    private final TrainingUtil trainingUtil;

    public TrainingService(TrainingRepository trainingRepository, TrainingFilter trainingFilter, TrainingUtil trainingUtil) {
        this.trainingRepository = trainingRepository;
        this.trainingFilter = trainingFilter;
        this.trainingUtil = trainingUtil;
    }

    public void save(Training training) {
        if (training.getCount() > 0 &&

                training.getActivity() != null && training.getStartTime() != null) {
            trainingRepository.save(training);
            LOGGER.info("Saved training: " + training.getActivity().getName());
        } else {
            LOGGER.error("Error to saving training");
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

    public boolean removeById(long trainingId) {
        LOGGER.debug("Removing training by training id " + trainingId);
        Training training = getById(trainingId).orElse(null);
        if (training != null) {
            training.setRemoved(true);
            trainingRepository.save(training);
            return true;
        } else {
            LOGGER.debug("Removing training by training id  " + trainingId + " is failed. TRAINING NOT FOUND");
        }
        return false;
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


    public Object groupUserTrainingByDate(long userId) {
        List<Training> trainingList = getEnabledTrainingByUserId(userId);
        return trainingUtil.groupedTrainingByDateMap(trainingList);
    }

    public boolean removeByIdAndActivityId(long trainingId, long activityId) {
        LOGGER.debug("Removing training by training id " + trainingId);
        Training training = getById(trainingId).orElse(null);
        if (training != null && training.getActivity().getId() == activityId) {
            training.setRemoved(true);
            trainingRepository.save(training);
            return true;
        } else {
            LOGGER.debug("Removing training by training id  " + trainingId + " is failed. TRAINING NOT FOUND");
            return false;
        }
    }

    public void saveAndAddToActivity(Training training, Activity activity) {
        activity.addTraining(training);
        save(training);
    }
}
