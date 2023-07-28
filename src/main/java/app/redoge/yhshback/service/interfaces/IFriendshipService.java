package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.dto.FriendshipRequestDto;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.UserNotFoundException;

public interface IFriendshipService {
    String createRequestOrFriendsByDto(FriendshipRequestDto dto) throws BadRequestException, UserNotFoundException;
}
