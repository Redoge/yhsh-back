package app.redoge.yhshback.service;

import app.redoge.yhshback.entity.ActivityType;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.repository.ActivityTypeRepository;
import app.redoge.yhshback.service.interfaces.IActivityTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ActivityTypeServiceService implements IActivityTypeService {
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
    public ActivityType getByName(String name) throws NotFoundException {
        return repository.findByName(name)
                .orElseThrow(()-> new NotFoundException("Activity type", name));
    }

    @Override
    public ActivityType getById(int id) throws NotFoundException {
        return repository.findById(id).orElseThrow(()-> new NotFoundException("Activity type", id));
    }
}
