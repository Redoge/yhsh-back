package app.redoge.yhshback.repository;


import app.redoge.yhshback.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository  extends JpaRepository<Activity,  Long> {
    void removeById(long id);
}
