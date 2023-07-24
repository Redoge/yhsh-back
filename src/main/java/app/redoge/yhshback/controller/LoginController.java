package app.redoge.yhshback.controller;

import app.redoge.yhshback.entity.Login;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.service.interfaces.ILoginService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static app.redoge.yhshback.utill.consts.Paths.LOGINS_PATH;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@RestController
@RequestMapping(LOGINS_PATH)
@AllArgsConstructor
public class LoginController {
    private final ILoginService loginService;

    @GetMapping
    public List<Login> getAll(@RequestParam(value = "userId", required = false) Long userId,
                              @RequestParam(value = "username", required = false) String username){
        if (isNotEmpty(userId))
            return loginService.getAllLoginByUserId(userId);
        if (isNotEmpty(username))
            return loginService.getAllLoginByUserUsername(username);
        return loginService.getAllLogin();
    }
    @GetMapping("/{id}")
    public Login getById(@PathVariable long id) throws NotFoundException {
        return loginService.getById(id);
    }
}
