package app.redoge.yhshback.repository;

import app.redoge.yhshback.entity.ActivationCode;
import app.redoge.yhshback.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivationCodeRepository extends JpaRepository<ActivationCode,  Long> {
    Optional<ActivationCode> findByCode(String code);
    @Transactional
    void removeByCode(String code);
    @Transactional
    void removeByUser(User user);
}