package app.redoge.yhshback.repository;

import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUserAndRemoved(User user, boolean b);

    List<Workout> findAllByUserUsernameAndRemovedAndTrainingsRemoved(String username, boolean removed, boolean trRemoved);

    List<Workout> findAllByUserIdAndRemovedAndTrainingsRemoved(Long userId, boolean removed, boolean trRemoved);
}
