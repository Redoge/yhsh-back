package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.Page;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.UserNotFoundException;
import app.redoge.yhshback.dto.UserActivityStatsDto;
import app.redoge.yhshback.service.interfaces.IStatsService;
import app.redoge.yhshback.service.interfaces.IUserService;
import app.redoge.yhshback.utill.mappers.PageMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static app.redoge.yhshback.utill.consts.Paths.USERS_PATH;
import static org.apache.commons.lang3.math.NumberUtils.isCreatable;

@RestController
@RequestMapping(USERS_PATH)
@AllArgsConstructor
public class StatsController {
    private final IUserService userService;
    private final IStatsService statsService;
    private final PageMapper<UserActivityStatsDto> pageMapper;

    @GetMapping("/{usernameOrId}/stats")
    public Page<UserActivityStatsDto> getStats(@PathVariable String usernameOrId,
                                               @RequestParam(value = "start", required = false) LocalDate start,
                                               @RequestParam(value = "end", required = false) LocalDate end,
                                               Pageable pageable) throws UserNotFoundException {
        User user;
        if(isCreatable(usernameOrId)){
            user =  userService.getUserById(Integer.parseInt(usernameOrId));
        }else{
            user =  userService.findUserByUsername(usernameOrId);
        }
        var dto = statsService.getUserActivityStatsListByUser(user, start, end);
        return pageMapper.mapToPage(dto, pageable);
    }
}
