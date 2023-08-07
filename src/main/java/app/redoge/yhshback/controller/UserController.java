package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.response.FriendsDto;
import app.redoge.yhshback.dto.response.FriendshipDto;
import app.redoge.yhshback.dto.response.UserDto;
import app.redoge.yhshback.entity.Friends;
import app.redoge.yhshback.entity.Friendship;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.dto.UserUpdateRequestDto;
import app.redoge.yhshback.service.interfaces.IFriendService;
import app.redoge.yhshback.service.interfaces.IFriendshipService;
import app.redoge.yhshback.service.interfaces.IUserService;
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
public class UserController {
    private final IUserService userService;
    private final ResponseDtoMapper responseDtoMapper;
    private final IFriendService friendService;
    private final IFriendshipService friendshipService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(value = "filterParam", required = false) String param,
                                  @RequestParam(value = "filterValue", required = false) String value) throws NotFoundException {
        return userService.getUsersByFilter(param, value)
                .stream()
                .map(responseDtoMapper::mapUserToUserDto)
                .toList();
    }
    @GetMapping("/{idOrUsername}")
    public UserDto getUserById(@PathVariable String idOrUsername) throws UserNotFoundException {
        User user;
        if(isCreatable(idOrUsername)){
            user = userService.getUserById(Integer.parseInt(idOrUsername));
        }else{
            user = userService.findUserByUsername(idOrUsername);
        }
        return responseDtoMapper.mapUserToUserDto(user);
    }

    @PostMapping
    public UserDto updateUser(@RequestBody UserUpdateRequestDto userUpdateRequest) throws  BadRequestException {
        var user = userService.updateUserByUserUpdateRequest(userUpdateRequest);
        return responseDtoMapper.mapUserToUserDto(user);
    }

    @GetMapping("/{idOrUsername}/friends")
    public List<UserDto> getUserByIdOrUsername(@PathVariable String idOrUsername) throws UserNotFoundException {
        List<User> friends;
        if(isCreatable(idOrUsername)){
            friends = friendService.findByUserId(parseLong(idOrUsername));
        }else{
            friends = friendService.findByUserUsername(idOrUsername);
        }
        return friends.stream().map(responseDtoMapper::mapUserToUserDto).toList();
    }

    @GetMapping("/{idOrUsername}/friends/requests")
    public List<FriendshipDto> getFriendRequestByIdOrUsername(@PathVariable String idOrUsername) throws UserNotFoundException {
        List<Friendship> friends;
        if(isCreatable(idOrUsername)){
            friends = friendshipService.findRequestByUserId(parseLong(idOrUsername));
        }else{
            friends = friendshipService.findRequestByUserUsername(idOrUsername);
        }
        return friends.stream().map(responseDtoMapper::mapFriendshipToFriendshipDto).toList();
    }
}
