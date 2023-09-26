package app.redoge.yhshback.utill;


import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.dto.UserActivityStatsDto;
import app.redoge.yhshback.utill.filter.TrainingFilter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class DtoUtil {
    private final TrainingFilter trainingFilter;
    public UserActivityStatsDto buildUserActivityStatsDtoByActivity(Activity activity){
        List<Integer> counts = trainingFilter.filterNotRemovedTraining(activity.getTrainings())
                .stream()
                .map(Training::getCount)
                .toList();
        return new UserActivityStatsDto(activity.getName(),
                counts.stream().mapToInt(t->t).sum(),
                counts.stream().mapToInt(t->t).min().orElse(0),
                counts.stream().mapToInt(t->t).max().orElse(0),
                (int) counts.stream().mapToInt(t->t).average().orElse(0),
                counts.size(), activity.getType().getNotation());
    }
}
