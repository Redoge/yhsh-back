package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.entity.ActivityType;
import app.redoge.yhshback.exception.NotFoundException;

import java.util.List;

public interface IActivityTypeService {
    ActivityType save(ActivityType activityType);

    List<ActivityType> getAll();

    ActivityType getByName(String name) throws NotFoundException;

    ActivityType getById(int id) throws NotFoundException;

    List<ActivityType> saveAll(List<ActivityType> activityTypes);
}
