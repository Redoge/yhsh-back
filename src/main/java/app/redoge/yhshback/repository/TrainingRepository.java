package app.redoge.yhshback.repository;


import app.redoge.yhshback.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepository  extends JpaRepository<Training, Long> {

    List<Training> findAllByRemoved(boolean removed);

    List<Training> getTrainingByActivityCreatorUsernameAndRemoved(String username, boolean b);

    List<Training> getTrainingByActivityCreatorIdAndRemoved(Long userId, boolean b);

    Optional<Training> findByIdAndRemoved(long id, boolean removed);

    List<Training> getTrainingByActivityCreatorIdAndRemovedAndActivityRemoved(Long userId, boolean b, boolean activityRemoved);

    List<Training> getTrainingByActivityCreatorUsernameAndRemovedAndActivityRemoved(String username, boolean b, boolean b1);

    List<Training> findAllByRemovedAndActivityRemoved(boolean b, boolean b1);
}
