package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.dto.ActivitySaveRequestDto;
import app.redoge.yhshback.dto.ActivityUpdateDto;
import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.exception.UserNotFoundException;

import java.util.List;


public interface IActivityService {
    List<Activity> getAll();
    Activity save(Activity activity) throws BadRequestException;
    Activity saveByDto(ActivitySaveRequestDto activitySaveRequestDto) throws BadRequestException, UserNotFoundException, NotFoundException;
    boolean removeById(long activityId) throws NotFoundException, BadRequestException;
    List<Activity> getAllByCreatorUsername(String username);
    List<Activity> getAllByCreatorId(Long userId);
    Activity getById(long id) throws NotFoundException;
    Activity updateByDto(Long id, ActivityUpdateDto activityRequestDto) throws NotFoundException, BadRequestException;
}
