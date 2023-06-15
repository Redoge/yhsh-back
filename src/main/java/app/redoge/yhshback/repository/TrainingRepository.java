package app.redoge.yhshback.repository;


import app.redoge.yhshback.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingRepository  extends JpaRepository<Training, Long> {
    List<Training> getTrainingByUserId(Long userId);
    void deleteById(long trainingId);
}
