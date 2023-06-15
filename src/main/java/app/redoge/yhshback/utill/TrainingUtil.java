package app.redoge.yhshback.utill;

import app.redoge.yhshback.entity.Training;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.*;

@Component
public class TrainingUtil {

    public Map<LocalDate, List<Training>> groupedTrainingByDateMap(List<Training> trainings){
        Map<LocalDate, List<Training>> trainingMap = new HashMap<>();
        if(trainings == null) return trainingMap;
        for(var training : trainings){
            if(training == null || training.getStartTime() == null) continue;
            LocalDate day =  training.getStartTime().toLocalDate();
            List<Training> trainingsList;
            trainingsList = trainingMap.get(day);
            if(trainingsList != null) {
                trainingsList.add(training);
            }else{
                trainingMap.put(day, new LinkedList<>(List.of(training)));
            }
        }
        return trainingMap;
    }

}
