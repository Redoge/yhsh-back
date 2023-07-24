package app.redoge.yhshback.repository;

import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUserAndRemoved(User user, boolean b);

    List<Workout> findAllByUserIdAndRemoved(Long userId, boolean removed);

    Optional<Workout> findByIdAndRemoved(Long id, boolean removed);

    List<Workout> findAllByUserUsernameAndRemoved(String username, boolean b);
}
