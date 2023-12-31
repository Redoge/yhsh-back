package app.redoge.yhshback.service;

import app.redoge.yhshback.dto.TrainingSaveRequestDto;
import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.enums.TrainingMode;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.repository.TrainingRepository;
import app.redoge.yhshback.service.interfaces.IActivityService;
import app.redoge.yhshback.service.interfaces.ITrainingService;
import app.redoge.yhshback.utill.validators.DtoValidators;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@Transactional
@AllArgsConstructor
public class TrainingService implements ITrainingService {
    private final TrainingRepository trainingRepository;
    private final IActivityService activityService;
    private final DtoValidators dtoValidators;

    @Override
    public Training save(Training training) throws BadRequestException {//TODO: encapsulate valid
        if (training.getCount() > 0 && isNotEmpty(training.getActivity()) && isNotEmpty(training.getStartTime())) {
            return trainingRepository.save(training);
        } else {
            throw new BadRequestException("Training not saved!!!");
        }
    }


    @PreAuthorize("@trainingService.getTrainingById(#trainingId).activity.creator.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    @Override
    public boolean removeById(long trainingId) throws NotFoundException {
        var training = getTrainingById(trainingId);
        training.setRemoved(true);
        trainingRepository.save(training);
        return true;
    }

    @PreAuthorize("#training.activity.creator.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    @Override
    public Training saveAndAddToActivity(Training training) throws BadRequestException {
        training.getActivity().addTraining(training);
        return save(training);
    }

    @PostAuthorize("returnObject.activity.creator.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    @Override
    public Training getTrainingById(long id) throws NotFoundException {
        return trainingRepository.findByIdAndRemovedAndActivityRemoved(id, false, false)
                .orElseThrow(() -> new NotFoundException("Training", id));
    }

    @PreAuthorize("@activityService.getById(#trainingDto.activityId()).creator.username.equalsIgnoreCase(authentication.name)")
    @Override
    public Training saveByDto(TrainingSaveRequestDto trainingDto) throws NotFoundException, BadRequestException {
        if (!dtoValidators.trainingSaveRequestDtoIsValid(trainingDto))
            throw new BadRequestException("Training not saved!!!");
        var activity = activityService.getById(trainingDto.activityId());
        return saveAndAddToActivity(Training.builder()
                .activity(activity)
                .startTime(trainingDto.start())
                .count(trainingDto.count())
                .mode(TrainingMode.SOLO)
                .weight(activity.isWithWeight() ? trainingDto.weight() : 0)
                .build());
    }

    @PreAuthorize("#username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    @Override
    public List<Training> getAllTrainingByUserUsername(String username) {
        return trainingRepository.getTrainingByActivityCreatorUsernameAndRemovedAndActivityRemoved(username, false, false)
                .stream().sorted(Comparator.comparing(Training::getStartTime).reversed()).toList();
    }

    @PreAuthorize("@userService.getUserById(#userId).username == authentication.name or hasAuthority('ADMIN')")
    @Override
    public List<Training> getAllTrainingByUserId(Long userId) {
        return trainingRepository.getTrainingByActivityCreatorIdAndRemovedAndActivityRemoved(userId, false, false)
                .stream().sorted(Comparator.comparing(Training::getStartTime).reversed()).toList();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public List<Training> getAllTraining() {
        return trainingRepository.findAllByRemovedAndActivityRemoved(false, false)
                .stream().sorted(Comparator.comparing(Training::getStartTime).reversed()).toList();
    }

    @PreAuthorize("#user.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    @Override
    public void removeAllTrainings(List<Training> trainingList, User user) {
        if (isNotEmpty(trainingList)) {
            for (var training : trainingList) {
                training.setRemoved(true);
            }
            trainingRepository.saveAll(trainingList);
        }
    }

    @Override
    public List<Training> addAllToActivity(List<Training> trainings) {
        for (var training : trainings) {
            training.getActivity().addTraining(training);
        }
        return trainings;
    }

    @Override
    public List<Training> saveAll(List<Training> trainings) {
        return trainingRepository.saveAll(trainings);
    }

    @PreAuthorize("@activityService.getById(#activityId).creator.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    @Override
    public List<Training> getAllTrainingByActivityId(long activityId) {
        return trainingRepository.getTrainingByActivityIdAndRemovedAndActivityRemoved(activityId, false, false)
                .stream().sorted(Comparator.comparing(Training::getStartTime).reversed()).toList();
    }
}
