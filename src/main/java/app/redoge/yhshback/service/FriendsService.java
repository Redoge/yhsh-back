package app.redoge.yhshback.service;

import app.redoge.yhshback.dto.FriendshipRequestDto;
import app.redoge.yhshback.dto.response.UserDto;
import app.redoge.yhshback.entity.Friends;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.repository.FriendsRepository;
import app.redoge.yhshback.service.interfaces.IFriendService;
import app.redoge.yhshback.utill.mappers.ResponseDtoMapper;
import app.redoge.yhshback.utill.validators.DtoValidators;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@AllArgsConstructor
public class FriendsService implements IFriendService {
    private final FriendsRepository repository;
    private final DtoValidators dtoValidators;


    @Transactional
    @Override
    @PreAuthorize("#username1.equalsIgnoreCase(authentication.name) or #username2.equalsIgnoreCase(authentication.name)")
    public boolean isFriendsByUsername(String username1, String username2) {
        return repository.existsByUser1UsernameAndUser2Username(username1, username2)
                || repository.existsByUser1UsernameAndUser2Username(username2, username1);
    }

    @Override
    @Transactional
    @PreAuthorize("#dto.senderUsername().equalsIgnoreCase(authentication.name) or #dto.recipientUsername().equalsIgnoreCase(authentication.name)")
    public boolean removeFriendsByFriendshipRequestDto(FriendshipRequestDto dto) throws BadRequestException {
        if(!dtoValidators.friendshipRequestDtoIsValid(dto))
            throw new BadRequestException("FriendshipRequestDto is not valid!!!");
        var friendCheck1 = repository.findByUser1UsernameAndUser2Username(dto.recipientUsername(), dto.senderUsername());
        if(isNotEmpty(friendCheck1)){
            this.repository.delete(friendCheck1);
            return true;
        }
        var friendCheck2 = repository.findByUser1UsernameAndUser2Username(dto.senderUsername(), dto.recipientUsername());
        if(isNotEmpty(friendCheck2)){
            this.repository.delete(friendCheck2);
            return true;
        }
        return false;
    }

    @Override
    public Friends saveByUsers(User sender, User recipient) {
        return repository.save(
                Friends.builder()
                        .user1(sender)
                        .user2(recipient)
                        .build()
        );
    }

    @Override
    @PreAuthorize("@userService.getUserById(#id).username.equalsIgnoreCase(authentication.name)")
    public List<User> findByUserId(long id){
        return repository.findByUser1IdOrUser2Id(id, id).stream().map(
                    friends-> {
                        if(friends.getUser1().getId()==id) return friends.getUser2();
                        return friends.getUser1();
                    }
                ).toList();
    }

    @Override
    @PreAuthorize("#username.equalsIgnoreCase(authentication.name)")
    public List<User> findByUserUsername(String username) {
        return repository.findAllByUser1UsernameOrUser2Username(username, username).stream().map(
                friends-> {
                    if(friends.getUser1().getUsername().equalsIgnoreCase(username)) return friends.getUser2();
                    return friends.getUser1();
                }
        ).toList();
    }
}
