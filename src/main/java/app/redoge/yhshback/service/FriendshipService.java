package app.redoge.yhshback.service;


import app.redoge.yhshback.dto.FriendshipRequestDto;
import app.redoge.yhshback.entity.Friendship;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.repository.FriendshipRepository;
import app.redoge.yhshback.service.interfaces.IFriendshipService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.String.format;

@AllArgsConstructor
@Service
public class FriendshipService implements IFriendshipService {
    private final FriendshipRepository repository;
    private final FriendsService friendsService;
    private final UserService userService;

    @Transactional
    @Override
    @PreAuthorize("#dto.senderUsername().equalsIgnoreCase(authentication.name) || hasAuthority('ADMIN')")
    public String createRequestOrFriendsByDto(FriendshipRequestDto dto) throws BadRequestException, UserNotFoundException {
        if (friendsService.isFriendsByUsername(dto.recipientUsername(), dto.senderUsername())) {
            throw new BadRequestException(format("%s and %s already is a friend!!", dto.senderUsername(), dto.recipientUsername()));
        }

        var checkRequestIsExist1 = repository.findBySenderUsernameAndRecipientUsername(dto.senderUsername(), dto.recipientUsername());
        var checkRequestIsExist2 = repository.findBySenderUsernameAndRecipientUsername(dto.recipientUsername(), dto.senderUsername());
        var sender = userService.findUserByUsername(dto.senderUsername());
        var recipient = userService.findUserByUsername(dto.recipientUsername());

        if (checkRequestIsExist2.isPresent()) {
            friendsService.saveByUsers(sender, recipient);
            repository.delete(checkRequestIsExist2.get());
            return "You are friends";
        } else {
            if (checkRequestIsExist1.isPresent()) {
                throw new BadRequestException("Request is exists!");
            }
            repository.save(Friendship.builder()
                    .sender(sender)
                    .recipient(recipient)
                    .created(LocalDateTime.now())
                    .build());
        }
        return "Created!";
    }

    @Override
    @PreAuthorize("@userService.getUserById(#id).username.equalsIgnoreCase(authentication.name)  || hasAuthority('ADMIN')")
    public List<Friendship> findRequestByUserId(long id) {
        return repository.findAllByRecipientId(id);
    }

    @Override
    @PreAuthorize("#username.equalsIgnoreCase(authentication.name)  || hasAuthority('ADMIN')")
    public List<Friendship> findRequestByUserUsername(String username) {
        return repository.findAllByRecipientUsername(username);
    }

    @Override
    @PreAuthorize("#dto.recipientUsername().equalsIgnoreCase(authentication.name) || #dto.senderUsername().equalsIgnoreCase(authentication.name) || hasAuthority('ADMIN')")
    public boolean removeByFriendshipDto(FriendshipRequestDto dto) {
        var request = repository.findBySenderUsernameAndRecipientUsername(dto.senderUsername(), dto.recipientUsername())
                .orElseThrow(()->new BadCredentialsException("Request not find!"));
        repository.delete(request);
        return true;
    }
}
