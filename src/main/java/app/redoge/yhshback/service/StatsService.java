package app.redoge.yhshback.service;


import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.dto.UserActivityStatsDto;
import app.redoge.yhshback.service.interfaces.IStatsService;
import app.redoge.yhshback.utill.DtoUtil;
import app.redoge.yhshback.utill.filter.ActivityFilter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StatsService implements IStatsService {
    private final DtoUtil statsUtil;
    private final ActivityFilter activityFilter;
    @Override
    public List<UserActivityStatsDto> getUserActivityStatsListByUser(User user){
        List<UserActivityStatsDto> activities = new ArrayList<>();
        for(var activity: activityFilter.filterNotRemovedActivities(user.getActivities())){
            activities.add(statsUtil.buildUserActivityStatsDtoByActivity(activity));
        }
        return activities;
    }
}
