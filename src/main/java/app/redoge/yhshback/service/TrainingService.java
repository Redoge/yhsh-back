package app.redoge.yhshback.service;

import app.redoge.yhshback.dto.TrainingSaveRequestDto;
import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.enums.TrainingMode;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.repository.TrainingRepository;
import app.redoge.yhshback.utill.validators.DtoValidators;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@Transactional
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final ActivityService activityService;
    private final DtoValidators dtoValidators;

    public TrainingService(TrainingRepository trainingRepository, ActivityService activityService, DtoValidators dtoValidators) {
        this.trainingRepository = trainingRepository;
        this.activityService = activityService;
        this.dtoValidators = dtoValidators;
    }

    public Training save(Training training) throws BadRequestException {//TODO: encapsulate valid
        if (training.getCount() > 0 &&
                isNotEmpty(training.getActivity()) && isNotEmpty(training.getStartTime())) {
            return trainingRepository.save(training);
        } else {
            throw new BadRequestException("Training not saved!!!");
        }
    }


    @PreAuthorize("@trainingService.getTrainingById(#trainingId).activity.creator.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    public boolean removeById(long trainingId) throws NotFoundException {
        var training = getTrainingById(trainingId);
        training.setRemoved(true);
        trainingRepository.save(training);
        return true;
    }
    @PreAuthorize("#activity.creator.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    public Training saveAndAddToActivity(Training training, Activity activity) throws BadRequestException {
        activity.addTraining(training);
        return save(training);
    }
    @PostAuthorize("returnObject.activity.creator.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    public Training getTrainingById(long id) throws NotFoundException {
        return trainingRepository.findByIdAndRemovedAndActivityRemoved(id, false, false)
                .orElseThrow(() -> new NotFoundException("Training", id));
    }

    @PreAuthorize("@activityService.getById(#trainingDto.activityId()).creator.username.equalsIgnoreCase(authentication.name)")
    public Training saveByDto(TrainingSaveRequestDto trainingDto) throws NotFoundException, BadRequestException {
        if(!dtoValidators.trainingSaveRequestDtoIsValid(trainingDto))
            throw new BadRequestException("Training not saved!!!");
        var activity = activityService.getById(trainingDto.activityId());
        return saveAndAddToActivity(Training.builder()
                .activity(activity)
                .startTime(trainingDto.start())
                .endTime(LocalDateTime.now())
                .count(trainingDto.count())
                .mode(TrainingMode.SOLO)
              .build(), activity);
    }
    @PreAuthorize("#username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    public List<Training> getAllTrainingByUserUsername(String username) {
        return trainingRepository.getTrainingByActivityCreatorUsernameAndRemovedAndActivityRemoved(username, false, false)
                .stream().sorted(Comparator.comparing(Training::getStartTime).reversed()).toList();
    }

    @PreAuthorize("@userService.getUserById(#userId).username == authentication.name or hasAuthority('ADMIN')")
    public List<Training> getAllTrainingByUserId(Long userId) {
        return trainingRepository.getTrainingByActivityCreatorIdAndRemovedAndActivityRemoved(userId, false, false)
                .stream().sorted(Comparator.comparing(Training::getStartTime).reversed()).toList();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Training> getAllTraining() {
        return trainingRepository.findAllByRemovedAndActivityRemoved(false, false)
                .stream().sorted(Comparator.comparing(Training::getStartTime).reversed()).toList();
    }

    @PreAuthorize("#user.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    public void removeAllTrainings(List<Training> trainingList, User user) {
        if (isNotEmpty(trainingList)) {
            for (var training : trainingList) {
                training.setRemoved(true);
            }
            trainingRepository.saveAll(trainingList);
        }
    }
}
