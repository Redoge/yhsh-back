package app.redoge.yhshback.service;


import app.redoge.yhshback.entity.Login;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.repository.LoginRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
public class LoginService {
    private static final Logger LOGGER = LogManager.getLogger(LoginService.class);
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
        LOGGER.info("Login: " + login.getUser().getUsername());
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<Login> getAllLogin(){
        LOGGER.debug("Get all login");
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
