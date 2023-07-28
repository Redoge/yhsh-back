package app.redoge.yhshback.repository;

import app.redoge.yhshback.entity.Friends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friends, Long> {
    boolean existsByUser1UsernameAndUser2Username(String user1_username, String user2_username);

    List<Friends> findByUser1IdAndUser2Id(long id, long id1);

    List<Friends> findAllByUser1UsernameOrUser2Username(String username, String username1);
    Friends findByUser1UsernameAndUser2Username(String username, String username1);
}
