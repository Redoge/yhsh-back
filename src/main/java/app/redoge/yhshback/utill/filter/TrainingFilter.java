package app.redoge.yhshback.utill.filter;

import app.redoge.yhshback.entity.Training;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingFilter {

    public List<Training> filterNotRemovedTraining(List<Training> training){
        return training.stream()
                .filter(t -> !t.isRemoved())
                .toList();
    }
}
