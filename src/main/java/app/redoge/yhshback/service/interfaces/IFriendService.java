package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.dto.FriendshipRequestDto;
import app.redoge.yhshback.entity.Friends;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.UserNotFoundException;

import java.util.List;

public interface IFriendService {
    List<User> findByUserId(long id) throws UserNotFoundException;
    List<User> findByUserUsername(String id);
    Friends saveByUsers(User sender, User recipient);
    boolean isFriendsByUsername(String username1, String username2);
    boolean removeFriendsByFriendshipRequestDto(FriendshipRequestDto dto) throws BadRequestException;
}
