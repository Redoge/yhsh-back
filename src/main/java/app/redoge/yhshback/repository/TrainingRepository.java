package app.redoge.yhshback.repository;


import app.redoge.yhshback.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepository  extends JpaRepository<Training, Long> {
    List<Training> getTrainingByActivityCreatorIdAndRemovedAndActivityRemoved(Long userId, boolean b, boolean activityRemoved);
    List<Training> getTrainingByActivityCreatorUsernameAndRemovedAndActivityRemoved(String username, boolean b, boolean b1);
    List<Training> findAllByRemovedAndActivityRemoved(boolean b, boolean b1);
    Optional<Training> findByIdAndRemovedAndActivityRemoved(long id, boolean b, boolean b1);
    List<Training> getTrainingByActivityIdAndRemovedAndActivityRemoved(long activityId, boolean b, boolean b1);
}
