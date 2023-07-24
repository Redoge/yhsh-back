package app.redoge.yhshback.utill.filter;

import app.redoge.yhshback.entity.Training;
import app.redoge.yhshback.entity.Workout;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingFilter {

    public List<Training> filterNotRemovedTraining(List<Training> training){
        return new ArrayList<>(training.stream()
                .filter(t -> !t.isRemoved())
                .toList());
    }

    public List<Workout> filterWorkoutListNotRemovedTraining(List<Workout> workouts){
        for(var workout : workouts){
            workout.setTrainings(filterNotRemovedTraining(workout.getTrainings()));
        }
        return workouts;
    }
}
