package app.redoge.yhshback.controller;

import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.dto.UserUpdateRequestDto;
import app.redoge.yhshback.service.interfaces.IUserService;
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

    @GetMapping
    public List<User> getUsers(@RequestParam(value = "filterParam", required = false) String param,
                               @RequestParam(value = "filterValue", required = false) String value) throws NotFoundException {
        return userService.getUsersByFilter(param, value);
    }
    @GetMapping("/{idOrUsername}")
    public User getUserById(@PathVariable String idOrUsername) throws UserNotFoundException {
        if(isCreatable(idOrUsername)){
            return userService.getUserById(Integer.parseInt(idOrUsername));
        }else{
            return userService.findUserByUsername(idOrUsername);
        }
    }

    @PostMapping
    public User updateUser(@RequestBody UserUpdateRequestDto userUpdateRequest) throws  BadRequestException {
        return userService.updateUserByUserUpdateRequest(userUpdateRequest);
    }
}
