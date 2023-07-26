package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.response.UserDto;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.dto.UserUpdateRequestDto;
import app.redoge.yhshback.service.interfaces.IUserService;
import app.redoge.yhshback.utill.mappers.ResponseDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.redoge.yhshback.utill.consts.Paths.USERS_PATH;
import static org.apache.commons.lang3.math.NumberUtils.isCreatable;

@RestController
@RequestMapping(USERS_PATH)
@AllArgsConstructor
public class UserController {
    private final IUserService userService;
    private final ResponseDtoMapper responseDtoMapper;

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
}
