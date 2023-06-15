package app.redoge.yhshback.service;


import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.pojo.UserActivityStatsPojo;
import app.redoge.yhshback.utill.PojoUtil;
import app.redoge.yhshback.utill.filter.ActivityFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatsService {
    private final PojoUtil statsUtil;
    private final ActivityFilter activityFilter;

    public StatsService(PojoUtil statsUtil, ActivityFilter activityFilter) {
        this.statsUtil = statsUtil;
        this.activityFilter = activityFilter;
    }

    public List<UserActivityStatsPojo> getUserActivityStatsListByUser(User user){
        List<UserActivityStatsPojo> activities = new ArrayList<>();
        for(var activity: activityFilter.filterNotRemovedActivities(user.getActivities())){
            activities.add(statsUtil.buildUserActivityStatsPojoByActivity(activity));
        }
        return activities;
    }
}
