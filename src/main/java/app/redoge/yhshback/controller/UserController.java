package app.redoge.yhshback.controller;

import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static app.redoge.yhshback.utill.paths.Constants.USERS_PATH;

@RestController
@RequestMapping(USERS_PATH)
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

}
