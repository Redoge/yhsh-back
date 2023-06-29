package app.redoge.yhshback.service;


import app.redoge.yhshback.dto.ActivitySaveRequestDto;
import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.repository.ActivityRepository;
import app.redoge.yhshback.repository.UserRepository;
import app.redoge.yhshback.utill.validators.DtoValidators;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


@Service
@Transactional
@AllArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final DtoValidators dtoValidators;

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Activity> getAll(){
        return activityRepository.findAllByRemoved(false);
    }

    @Transactional
    public void save(Activity activity) {
        if(isNotEmpty(activity.getName())){
            activityRepository.save(activity);
        }
    }
    @PreAuthorize("#activitySaveRequestDto.username().equalsIgnoreCase(authentication.name)")
    public Activity saveByDto(ActivitySaveRequestDto activitySaveRequestDto) throws BadRequestException {
        if(!dtoValidators.activitySaveRequestDtoIsValid(activitySaveRequestDto))
            throw new BadRequestException("Activity not valid!!!");

        var creator = userRepository.findByUsername(activitySaveRequestDto.username())
                .orElseThrow(()-> new UsernameNotFoundException(activitySaveRequestDto.username()));

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
    public boolean removeById(long activityId) throws NotFoundException {
        Activity activity = getById(activityId);
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
        return activityRepository.findByIdAndRemoved(id, false).orElseThrow(()-> new NotFoundException("Activity", id));
    }
}
