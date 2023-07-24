package app.redoge.yhshback.controller;

import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.dto.UserActivityStatsDto;
import app.redoge.yhshback.service.interfaces.IStatsService;
import app.redoge.yhshback.service.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static app.redoge.yhshback.utill.consts.Paths.USERS_PATH;
import static org.apache.commons.lang3.math.NumberUtils.isCreatable;

@RestController
@RequestMapping(USERS_PATH)
@AllArgsConstructor
public class StatsController {
    private final IUserService userService;
    private final IStatsService statsService;
    @GetMapping("/{usernameOrId}/stats")
    public List<UserActivityStatsDto> getStats(@PathVariable String usernameOrId) throws UserNotFoundException {
        User user;
        if(isCreatable(usernameOrId)){
            user =  userService.getUserById(Integer.parseInt(usernameOrId));
        }else{
            user =  userService.findUserByUsername(usernameOrId);
        }
        return statsService.getUserActivityStatsListByUser(user);
    }
}
