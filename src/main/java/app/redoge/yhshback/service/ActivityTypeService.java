package app.redoge.yhshback.service;

import app.redoge.yhshback.entity.ActivityType;
import app.redoge.yhshback.repository.ActivityTypeRepository;
import app.redoge.yhshback.service.interfaces.IActivityType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ActivityTypeService implements IActivityType {
    private final ActivityTypeRepository repository;

    @Override
    public ActivityType save(ActivityType activityType) {
        return repository.save(activityType);
    }
    @Override
    public List<ActivityType> saveAll(List<ActivityType> activityTypes) {
        return repository.saveAll(activityTypes);
    }
    @Override
    public List<ActivityType> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ActivityType> getByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Optional<ActivityType> getById(int id) {
        return repository.findById(id);
    }
}
