package app.redoge.yhshback.service;


import app.redoge.yhshback.dto.ActivitySaveRequestDto;
import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.repository.ActivityRepository;
import app.redoge.yhshback.repository.UserRepository;
import app.redoge.yhshback.utill.validators.DtoValidators;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


@Service
@Transactional
public class ActivityService {
    private static final Logger LOGGER = LogManager.getLogger(ActivityService.class);
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final DtoValidators dtoValidators;

    public ActivityService(ActivityRepository activityRepository, UserRepository userRepository, DtoValidators dtoValidators) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.dtoValidators = dtoValidators;
    }
    @Transactional
    public List<Activity> getAll(){
        return activityRepository.findAll();
    }

    @Transactional
    public Activity getById(long id) throws NotFoundException {
        LOGGER.debug("Activity by id " + id);
        return activityRepository.findById(id).orElseThrow(()-> new NotFoundException("Activity", id));
    }
    @Transactional
    public void save(Activity activity) {
        if(isNotEmpty(activity.getName())){
            activityRepository.save(activity);
            LOGGER.info("Saving activity " + activity.getName());
        }else{
            LOGGER.error("Activity name null or empty");
        }
    }
    @Transactional
    public boolean removeById(long activityId) throws NotFoundException {
        Activity activity = getById(activityId);
        activity.setRemoved(true);
        save(activity);
        LOGGER.info("Remove activity " + activity.getName());
        return true;
    }

    public List<Activity> getAllByCreatorId(Long userId) {
        return activityRepository.findByCreatorId(userId);
    }

    public Activity saveByDto(ActivitySaveRequestDto activitySaveRequestDto) throws UserNotFoundException, BadRequestException {
        if(!dtoValidators.activitySaveRequestDtoIsValid(activitySaveRequestDto))
            throw new BadRequestException("Activity not valid!!!");

        var creator = userRepository.findById(activitySaveRequestDto.creatorId())
                .orElseThrow(()-> new UserNotFoundException(activitySaveRequestDto.creatorId()));

        var activity = Activity.builder()
                .creator(creator)
                .removed(false)
                .name(activitySaveRequestDto.name())
                .notation(activitySaveRequestDto.notation())
                .build();
        return  activityRepository.save(activity);
    }
}
