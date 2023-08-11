package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.Page;
import app.redoge.yhshback.dto.response.FriendshipDto;
import app.redoge.yhshback.dto.response.UserDto;
import app.redoge.yhshback.entity.Friendship;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.dto.UserUpdateRequestDto;
import app.redoge.yhshback.service.interfaces.IFriendService;
import app.redoge.yhshback.service.interfaces.IFriendshipService;
import app.redoge.yhshback.service.interfaces.IUserService;
import app.redoge.yhshback.utill.mappers.PageMapper;
import app.redoge.yhshback.utill.mappers.ResponseDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    private final PageMapper<UserDto> pageMapper;

    @GetMapping
    public Page<UserDto> getUsers(@RequestParam(value = "filterParam", required = false) String param,
                                  @RequestParam(value = "filterValue", required = false) String value,
                                  Pageable pageable) throws NotFoundException {
        var dto = userService.getUsersByFilter(param, value)
                .stream()
                .map(responseDtoMapper::mapUserToUserDto)
                .toList();
        return pageMapper.mapToPage(dto, pageable);
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
    public Page<UserDto> getUserByIdOrUsername(@PathVariable String idOrUsername,
                                               Pageable pageable) throws UserNotFoundException {
        List<User> friends;
        if(isCreatable(idOrUsername)){
            friends = friendService.findByUserId(parseLong(idOrUsername));
        }else{
            friends = friendService.findByUserUsername(idOrUsername);
        }
        var dto = friends.stream().map(responseDtoMapper::mapUserToUserDto).toList();
        return pageMapper.mapToPage(dto, pageable);
    }

    @GetMapping("/{idOrUsername}/friends/requests")
    public List<FriendshipDto> getFriendRequestByIdOrUsername(@PathVariable String idOrUsername){
        List<Friendship> friends;
        if(isCreatable(idOrUsername)){
            friends = friendshipService.findRequestByUserId(parseLong(idOrUsername));
        }else{
            friends = friendshipService.findRequestByUserUsername(idOrUsername);
        }
        return friends.stream().map(responseDtoMapper::mapFriendshipToFriendshipDto).toList();
    }
}
