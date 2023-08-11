package app.redoge.yhshback.utill.filter;

import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.entity.Workout;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Component
public class TrainingFilter {

    public List<Training> filterNotRemovedTraining(List<Training> training){
        return new ArrayList<>(training.stream()
                .filter(t -> !t.isRemoved() && !t.getActivity().isRemoved())
                .toList());
    }


    public List<Training> filterByStartAndEndTime(List<Training> trainings, LocalDate start, LocalDate end) {
        if(isNull(start)){
            start = LocalDate.MIN;
        }
        if(isNull(end)){
            end = LocalDate.MAX;
        }
        var finalStart = start.atStartOfDay();
        var finalEnd = end.atTime(23, 59, 59);
        return trainings.stream()
                .filter(
                        training -> training
                                .getStartTime()
                                .isAfter(finalStart) && training
                                .getStartTime()
                                .isBefore(finalEnd))
                .toList();
    }
}
