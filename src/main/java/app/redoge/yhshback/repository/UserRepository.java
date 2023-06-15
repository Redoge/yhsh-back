package app.redoge.yhshback.repository;


import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String userName);
    List<User> findByUserRole(UserRole userRole);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
