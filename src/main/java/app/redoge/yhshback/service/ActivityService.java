package app.redoge.yhshback.service;


import app.redoge.yhshback.dto.ActivitySaveRequestDto;
import app.redoge.yhshback.dto.ActivityUpdateDto;
import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.repository.ActivityRepository;
import app.redoge.yhshback.service.interfaces.IActivityService;
import app.redoge.yhshback.service.interfaces.IActivityTypeService;
import app.redoge.yhshback.service.interfaces.IUserService;
import app.redoge.yhshback.utill.filter.TrainingFilter;
import app.redoge.yhshback.utill.validators.DtoValidators;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


@Service
@Transactional
@AllArgsConstructor
public class ActivityService implements IActivityService {
    private final ActivityRepository activityRepository;
    private final IUserService userService;
    private final IActivityTypeService activityTypeService;
    private final DtoValidators dtoValidators;
    private final TrainingFilter trainingFilter;

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public List<Activity> getAll(){
        return activityRepository.findAllByRemoved(false);
    }

    @Transactional
    @Override
    public Activity save(Activity activity) throws BadRequestException {
        if(isNotEmpty(activity.getName())){
            return activityRepository.save(activity);
        }else{
            throw new BadRequestException("Activity name can't be empty");
        }
    }
    @PreAuthorize("#activitySaveRequestDto.username().equalsIgnoreCase(authentication.name)")
    @Override
    public Activity saveByDto(ActivitySaveRequestDto activitySaveRequestDto) throws BadRequestException, UserNotFoundException, NotFoundException {
        if(!dtoValidators.activitySaveRequestDtoIsValid(activitySaveRequestDto))
            throw new BadRequestException("Activity not valid!!!");
        var creator = userService.findUserByUsername(activitySaveRequestDto.username());
        var activityType = activityTypeService.getByName(activitySaveRequestDto.typeName());
        var activity = Activity.builder()
                .creator(creator)
                .removed(false)
                .name(activitySaveRequestDto.name())
                .type(activityType)
                .withWeight(activitySaveRequestDto.withWeight())
                .build();
        return  activityRepository.save(activity);
    }
    @Transactional
    @PreAuthorize("@activityService.getById(#activityId).creator.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    @Override
    public boolean removeById(long activityId) throws NotFoundException, BadRequestException {
        var activity = getById(activityId);
        activity.setRemoved(true);
        save(activity);
        return true;
    }
    @PreAuthorize("#username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    @Override
    public List<Activity> getAllByCreatorUsername(String username) {
        return activityRepository.findByCreatorUsernameAndRemoved(username, false);
    }

    @PreAuthorize("@userService.getUserById(#userId).username == authentication.name or hasAuthority('ADMIN')")
    @Override
    public List<Activity> getAllByCreatorId(Long userId) {
        return activityRepository.findByCreatorIdAndRemoved(userId, false);
    }
    @Transactional
    @PostAuthorize("returnObject.creator.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    @Override
    public Activity getById(long id) throws NotFoundException {
        var activity =  activityRepository.findByIdAndRemoved(id, false).
                orElseThrow(()-> new NotFoundException("Activity", id));
        var training = trainingFilter.filterNotRemovedTraining(activity.getTrainings());
        activity.setTrainings(training);
        return activity;
    }
    @Transactional
    @PreAuthorize("@activityService.getById(#id).creator.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    @Override
    public Activity updateByDto(Long id, ActivityUpdateDto activityRequestDto) throws NotFoundException, BadRequestException {
        if(!id.equals(activityRequestDto.id()))
            throw new BadRequestException("Activity id not equal path id");
        var activity = getById(id);
        var activityType = activityTypeService.getByName(activityRequestDto.typeName());
        if(dtoValidators.activityUpdateDtoIsValid(activityRequestDto)){
            activity.setName(activityRequestDto.name());
            activity.setType(activityType);
            return save(activity);
        }
        throw new BadRequestException("Activity name and notation can't be empty");
    }
}
