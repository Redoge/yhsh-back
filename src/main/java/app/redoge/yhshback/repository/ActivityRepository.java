package app.redoge.yhshback.repository;


import app.redoge.yhshback.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository  extends JpaRepository<Activity,  Long> {
    void removeById(long id);

    Optional<Activity> findByIdAndRemoved(long activityId, boolean isRemoved);

    List<Activity> findByCreatorId(Long userId);

    List<Activity> findByCreatorUsername(String username);

    List<Activity> findByCreatorIdAndRemoved(Long userId, boolean b);

    List<Activity> findByCreatorUsernameAndRemoved(String username, boolean b);

    List<Activity> findAllByRemoved(boolean b);
}
