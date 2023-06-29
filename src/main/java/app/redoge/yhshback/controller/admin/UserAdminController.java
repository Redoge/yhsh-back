package app.redoge.yhshback.controller.admin;

import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import static app.redoge.yhshback.utill.paths.Constants.ADMIN_USER_PATH;

@RestController
@RequestMapping(ADMIN_USER_PATH)
@AllArgsConstructor
public class UserAdminController {
    private final UserService userService;

    @PostMapping("/change/role")
    public boolean changeUserRoleById(@RequestBody Id id) throws BadRequestException {
        userService.changeUserRoleByUserId(id.getId());
        return true;
    }

    @PostMapping("/change/enabled")
    public boolean changeUserEnabledById(@RequestBody Id id) throws BadRequestException {
        userService.changeEnabledByUserId(id.getId());
        return true;
    }


    @Data
    static class Id {
        private Long id;
    }
}
