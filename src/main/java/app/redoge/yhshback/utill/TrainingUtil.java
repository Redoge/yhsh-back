package app.redoge.yhshback.utill;

import app.redoge.yhshback.entity.Training;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.*;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Component
public class TrainingUtil {

    public Map<LocalDate, List<Training>> groupedTrainingByDateMap(List<Training> trainings){
        Map<LocalDate, List<Training>> trainingMap = new HashMap<>();
        if(isEmpty(trainings)) return trainingMap;
        for(var training : trainings){
            if(isEmpty(training) || isEmpty(training.getStartTime())) continue;
            LocalDate day =  training.getStartTime().toLocalDate();
            List<Training> trainingsList;
            trainingsList = trainingMap.get(day);
            if(isNotEmpty(trainingsList)) {
                trainingsList.add(training);
            }else{
                trainingMap.put(day, new LinkedList<>(List.of(training)));
            }
        }
        return trainingMap;
    }

}
