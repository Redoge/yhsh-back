package app.redoge.yhshback.service;


import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.dto.UserActivityStatsDto;
import app.redoge.yhshback.service.interfaces.IStatsService;
import app.redoge.yhshback.utill.DtoUtil;
import app.redoge.yhshback.utill.filter.ActivityFilter;
import app.redoge.yhshback.utill.filter.TrainingFilter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class StatsService implements IStatsService {
    private final DtoUtil statsUtil;
    private final ActivityFilter activityFilter;
    private final TrainingFilter trainingFilter;
    @Override
    public List<UserActivityStatsDto> getUserActivityStatsListByUser(User user, LocalDate start, LocalDate end){
        List<UserActivityStatsDto> activities = new ArrayList<>();
        for(var activity: activityFilter.filterNotRemovedActivities(user.getActivities())){
            var filteredTrainings = trainingFilter.filterByStartAndEndTime(activity.getTrainings(), start, end);
            activity.setTrainings(filteredTrainings);
            activities.add(statsUtil.buildUserActivityStatsDtoByActivity(activity));
        }
        return activities;
    }
}
