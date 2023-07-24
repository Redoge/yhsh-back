package app.redoge.yhshback.utill.filter;

import app.redoge.yhshback.entity.Activity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityFilter {
    public List<Activity> filterNotRemovedActivities(List<Activity> activities) {
        return new ArrayList<>(activities.stream()
                .filter(activity -> !activity.isRemoved())
                .toList());
    }
}
