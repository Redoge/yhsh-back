package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.FriendshipRequestDto;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.service.interfaces.IFriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static app.redoge.yhshback.utill.consts.Paths.FRIENDSHIP_PATH;


@RestController
@RequestMapping(FRIENDSHIP_PATH)
@RequiredArgsConstructor
public class FriendshipController {
    private final IFriendshipService service;
    @PostMapping
    public boolean createFriendshipRequest(@RequestBody FriendshipRequestDto dto) throws UserNotFoundException, BadRequestException {
        service.createRequestOrFriendsByDto(dto);
        return true;
    }
    @PostMapping("/remove")
    public boolean removeFriendshipRequest(@RequestBody FriendshipRequestDto dto) throws UserNotFoundException, BadRequestException {
        service.removeByFriendshipDto(dto);
        return true;
    }
}
