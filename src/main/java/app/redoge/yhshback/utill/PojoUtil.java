package app.redoge.yhshback.utill;


import app.redoge.yhshback.entity.Activity;
import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.pojo.UserActivityStatsPojo;
import app.redoge.yhshback.utill.filter.TrainingFilter;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PojoUtil {
    private final TrainingFilter trainingFilter;

    public PojoUtil(TrainingFilter trainingFilter) {
        this.trainingFilter = trainingFilter;
    }

    public UserActivityStatsPojo buildUserActivityStatsPojoByActivity(Activity activity){
        List<Integer> counts = trainingFilter.filterNotRemovedTraining(activity.getTrainings())
                .stream()
                .map(Training::getCount)
                .toList();
        return new UserActivityStatsPojo(activity.getName(),
                counts.stream().mapToInt(t->t).sum(),
                counts.stream().mapToInt(t->t).min().orElse(0),
                counts.stream().mapToInt(t->t).max().orElse(0),
                (int) counts.stream().mapToInt(t->t).average().orElse(0),
                counts.size(), activity.getNotation());
    }
}
