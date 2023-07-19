package app.redoge.yhshback.service;


import app.redoge.yhshback.dto.ActivitySaveRequestDto;
import app.redoge.yhshback.dto.ActivityUpdateDto;
import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.repository.ActivityRepository;
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
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserService userService;
    private final DtoValidators dtoValidators;

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Activity> getAll(){
        return activityRepository.findAllByRemoved(false);
    }

    @Transactional
    public Activity save(Activity activity) throws BadRequestException {
        if(isNotEmpty(activity.getName())){
            return activityRepository.save(activity);
        }else{
            throw new BadRequestException("Activity name can't be empty");
        }
    }
    @PreAuthorize("#activitySaveRequestDto.username().equalsIgnoreCase(authentication.name)")
    public Activity saveByDto(ActivitySaveRequestDto activitySaveRequestDto) throws BadRequestException, UserNotFoundException {
        if(!dtoValidators.activitySaveRequestDtoIsValid(activitySaveRequestDto))
            throw new BadRequestException("Activity not valid!!!");
        var creator = userService.findUserByUsername(activitySaveRequestDto.username());
        var activity = Activity.builder()
                .creator(creator)
                .removed(false)
                .name(activitySaveRequestDto.name())
                .notation(activitySaveRequestDto.notation())
                .build();
        return  activityRepository.save(activity);
    }
    @Transactional
    @PreAuthorize("@activityService.getById(#activityId).creator.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    public boolean removeById(long activityId) throws NotFoundException, BadRequestException {
        var activity = getById(activityId);
        activity.setRemoved(true);
        save(activity);
        return true;
    }
    @PreAuthorize("#username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    public List<Activity> getAllByCreatorUsername(String username) {
        return activityRepository.findByCreatorUsernameAndRemoved(username, false);
    }

    @PreAuthorize("@userService.getUserById(#userId).username == authentication.name or hasAuthority('ADMIN')")
    public List<Activity> getAllByCreatorId(Long userId) {
        return activityRepository.findByCreatorIdAndRemoved(userId, false);
    }
    @Transactional
    @PostAuthorize("returnObject.creator.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    public Activity getById(long id) throws NotFoundException {
        return activityRepository.findByIdAndRemoved(id, false).
                orElseThrow(()-> new NotFoundException("Activity", id));
    }
    @Transactional
    @PreAuthorize("@activityService.getById(#id).creator.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    public Activity updateByDto(Long id, ActivityUpdateDto activityRequestDto) throws NotFoundException, BadRequestException {
        if(!id.equals(activityRequestDto.id()))
            throw new BadRequestException("Activity id not equal path id");
        var activity = getById(id);
        if(dtoValidators.activityUpdateDtoIsValid(activityRequestDto)){
            activity.setName(activityRequestDto.name());
            activity.setNotation(activityRequestDto.notation());
            return save(activity);
        }
        throw new BadRequestException("Activity name and notation can't be empty");
    }
}
