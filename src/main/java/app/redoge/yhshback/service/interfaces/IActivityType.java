package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.entity.ActivityType;

import java.util.List;
import java.util.Optional;

public interface IActivityType {
    ActivityType save(ActivityType activityType);

    List<ActivityType> getAll();

    Optional<ActivityType> getByName(String name);

    Optional<ActivityType> getById(int id);

    List<ActivityType> saveAll(List<ActivityType> activityTypes);
}
