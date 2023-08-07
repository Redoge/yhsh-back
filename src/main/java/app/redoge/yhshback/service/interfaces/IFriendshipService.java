package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.dto.FriendshipRequestDto;
import app.redoge.yhshback.entity.Friendship;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.UserNotFoundException;

import java.util.List;

public interface IFriendshipService {
    String createRequestOrFriendsByDto(FriendshipRequestDto dto) throws BadRequestException, UserNotFoundException;

    List<Friendship> findRequestByUserId(long l);

    List<Friendship> findRequestByUserUsername(String idOrUsername);

    boolean removeByFriendshipDto(FriendshipRequestDto dto);
}
