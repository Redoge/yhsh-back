package app.redoge.yhshback.service;


import app.redoge.yhshback.entity.Login;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.repository.LoginRepository;
import app.redoge.yhshback.service.interfaces.ILoginService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
@AllArgsConstructor
public class LoginService implements ILoginService {
    private final LoginRepository loginRepository;
    @Override
    @PreAuthorize("@userService.getUserById(#userId).username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    public List<Login> getAllLoginByUserId(long userId) {
        return loginRepository.findAllByUserId(userId);
    }
    @Override
    public void addLogin(Login login) {
        loginRepository.save(login);
    }
    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Login> getAllLogin() {
        return loginRepository.findAll();
    }
    @Override
    @PreAuthorize("#user.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    public void addLoginByUser(User user) {
        var login = Login.builder()
                .user(user)
                .loginTime(LocalDateTime.now())
                .build();
        addLogin(login);
    }
    @Override
    @PostAuthorize("returnObject.user.username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    public Login getById(long id) throws NotFoundException {
        return loginRepository.findById(id).orElseThrow(() -> new NotFoundException("Login", id));
    }
    @Override
    @PreAuthorize("#username.equalsIgnoreCase(authentication.name) or hasAuthority('ADMIN')")
    public List<Login> getAllLoginByUserUsername(String username) {
        return loginRepository.findAllByUserUsername(username);
    }
}
