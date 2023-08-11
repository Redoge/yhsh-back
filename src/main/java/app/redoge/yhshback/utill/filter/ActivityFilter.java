package app.redoge.yhshback.utill.filter;

import app.redoge.yhshback.entity.Activity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActivityFilter {
    public List<Activity> filterNotRemovedActivities(List<Activity> activities) {
        return new ArrayList<>(activities.stream()
                .filter(activity -> !activity.isRemoved())
                .toList());
    }
}
