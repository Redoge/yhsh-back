package app.redoge.yhshback.repository;

import app.redoge.yhshback.entity.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, Integer> {
    Optional<ActivityType> findByName(String name);
}
