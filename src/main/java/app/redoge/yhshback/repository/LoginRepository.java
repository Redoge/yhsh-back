package app.redoge.yhshback.repository;

import app.redoge.yhshback.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LoginRepository  extends JpaRepository<Login, Long> {
    List<Login> findAllByUserId(long userId);

    List<Login> findAllByUserUsername(String username);
}
