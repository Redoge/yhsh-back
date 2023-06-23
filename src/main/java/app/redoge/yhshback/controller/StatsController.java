package app.redoge.yhshback.controller;

import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.pojo.UserActivityStatsPojo;
import app.redoge.yhshback.service.StatsService;
import app.redoge.yhshback.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static app.redoge.yhshback.utill.paths.Constants.USERS_PATH;
import static org.apache.commons.lang3.math.NumberUtils.isCreatable;

@RestController
@RequestMapping(USERS_PATH)
@AllArgsConstructor
public class StatsController {
    private final UserService userService;
    private final StatsService statsService;
    @GetMapping("/{usernameOrId}/stats")
    public List<UserActivityStatsPojo> getStats(@PathVariable String usernameOrId) throws UserNotFoundException {
        User user;
        if(isCreatable(usernameOrId)){
            user =  userService.getUserById(Integer.parseInt(usernameOrId));
        }else{
            user =  userService.findUserByUsername(usernameOrId);
        }
        return statsService.getUserActivityStatsListByUser(user);
    }
}
