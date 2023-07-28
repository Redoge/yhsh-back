package app.redoge.yhshback.repository;

import app.redoge.yhshback.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    List<Friendship> findAllByRecipientId(long recipient_id);

    Optional<Friendship> findBySenderUsernameAndRecipientUsername(String sender_username, String recipient_username);
}
