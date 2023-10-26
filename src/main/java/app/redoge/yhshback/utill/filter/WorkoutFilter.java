package app.redoge.yhshback.utill.filter;

import app.redoge.yhshback.entity.Workout;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class WorkoutFilter {
    private final TrainingFilter trainingFilter;
    public List<Workout> filterWorkoutListByNotEmptyAndNotRemovedTraining(List<Workout> workouts){
        var filteredWorkouts = new ArrayList<Workout>();
        for(var workout : workouts){
            var trainings = trainingFilter.filterNotRemovedTraining(workout.getTrainings());
            if(!trainings.isEmpty()){
                workout.setTrainings(trainings);
                filteredWorkouts.add(workout);
            }
        }
        return filteredWorkouts;
    }
}
