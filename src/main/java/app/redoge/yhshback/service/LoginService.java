package app.redoge.yhshback.service;


import app.redoge.yhshback.entity.Login;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.repository.LoginRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class LoginService {
    private final LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @PreAuthorize("@userService.getUserById(#userId).username.equalsIgnoreCase(authentication.name) or hasRole('ADMIN')")
    public List<Login> getAllLoginByUserId(long userId){
        return loginRepository.findAllByUserId(userId);
    }
    public void addLogin(Login login){
        loginRepository.save(login);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<Login> getAllLogin(){
        return loginRepository.findAll();
    }

    @PreAuthorize("#user.username.equalsIgnoreCase(authentication.name) or hasRole('ADMIN')")
    public void addLoginByUser(User user) {
        var login = Login.builder()
                .user(user)
                .loginTime(LocalDateTime.now())
                .build();
        addLogin(login);
    }

    @PostAuthorize("returnObject.user.username.equalsIgnoreCase(authentication.name) or hasRole('ADMIN')")
    public Login getById(long id) throws NotFoundException {
        return loginRepository.findById(id).orElseThrow(() -> new NotFoundException("Login", id));
    }
    @PreAuthorize("#username.equalsIgnoreCase(authentication.name) or hasRole('ADMIN')")
    public List<Login> getAllLoginByUserUsername(String username) {
        return loginRepository.findAllByUserUsername(username);
    }
}
