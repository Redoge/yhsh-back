package app.redoge.yhshback.service;


import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.repository.ActivityRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
public class ActivityService {
    private static final Logger LOGGER = LogManager.getLogger(ActivityService.class);
    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Transactional
    public Optional<Activity> getActivityById(long id){
        LOGGER.debug("Activity by id " + id);
        return activityRepository.findById(id);
    }
    @Transactional
    public void save(Activity activity) {
        if(activity.getName()!= null && activity.getName().trim().length()!=0){
            LOGGER.info("Saving activity " + activity.getName());
            activityRepository.save(activity);
        }else{
            LOGGER.error("Activity name null or empty");
        }
    }
    @Transactional
    public void saveWithCreator(Activity activity, User user) {
        if(activity.getName()!= null && activity.getName().trim().length()!=0){
            activity.setCreator(user);
            activityRepository.save(activity);
            LOGGER.info("Saving activity " + activity.getName());
        }else{
            LOGGER.error("Activity name null or empty");
        }
    }

    @Transactional
    public void removeById(long activityId) {
        Activity activity = getActivityById(activityId).orElse(null);
        if(activity != null) {
            activity.setRemoved(true);
            save(activity);
            LOGGER.info("Remove activity " + activity.getName());
        }else{
            LOGGER.error("Incorrect activity");
        }
    }
}
