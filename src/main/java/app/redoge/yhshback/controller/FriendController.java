package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.FriendshipRequestDto;
import app.redoge.yhshback.dto.response.FriendsDto;
import app.redoge.yhshback.entity.Friends;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.service.interfaces.IFriendService;
import app.redoge.yhshback.utill.mappers.ResponseDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.redoge.yhshback.utill.consts.Paths.USERS_PATH;
import static java.lang.Long.parseLong;
import static org.apache.commons.lang3.math.NumberUtils.isCreatable;
@RestController
@RequestMapping(USERS_PATH)
@AllArgsConstructor
public class FriendController {
    private final IFriendService friendService;
    private final ResponseDtoMapper responseDtoMapper;

    @GetMapping("/{idOrUsername}/friends")
    public List<FriendsDto> getUserByIdOrUsername(@PathVariable String idOrUsername){
        List<Friends> friends;
        if(isCreatable(idOrUsername)){
            friends = friendService.findByUserId(parseLong(idOrUsername));
        }else{
            friends = friendService.findByUserUsername(idOrUsername);
        }
        return friends.stream().map(responseDtoMapper::mapFriendsToFriendsDto).toList();
    }

    @PostMapping("/friends/remove")
    public boolean removeFriends(@RequestBody FriendshipRequestDto dto) throws BadRequestException {
        return friendService.removeFriendsByFriendshipRequestDto(dto);
    }
}
