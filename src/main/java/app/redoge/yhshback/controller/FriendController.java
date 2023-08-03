package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.FriendshipRequestDto;

import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.service.interfaces.IFriendService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


import static app.redoge.yhshback.utill.consts.Paths.FRIENDS_PATH;

@RestController
@RequestMapping(FRIENDS_PATH)
@AllArgsConstructor
public class FriendController {
    private final IFriendService friendService;

    @PostMapping ("/remove")
    public boolean removeFriends(@RequestBody FriendshipRequestDto dto) throws BadRequestException {
        return friendService.removeFriendsByFriendshipRequestDto(dto);
    }
}
