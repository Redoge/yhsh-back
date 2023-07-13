package app.redoge.yhshback.service;


import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.dto.UserActivityStatsDto;
import app.redoge.yhshback.utill.DtoUtil;
import app.redoge.yhshback.utill.filter.ActivityFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatsService {
    private final DtoUtil statsUtil;
    private final ActivityFilter activityFilter;

    public StatsService(DtoUtil statsUtil, ActivityFilter activityFilter) {
        this.statsUtil = statsUtil;
        this.activityFilter = activityFilter;
    }

    public List<UserActivityStatsDto> getUserActivityStatsListByUser(User user){
        List<UserActivityStatsDto> activities = new ArrayList<>();
        for(var activity: activityFilter.filterNotRemovedActivities(user.getActivities())){
            activities.add(statsUtil.buildUserActivityStatsDtoByActivity(activity));
        }
        return activities;
    }
}
