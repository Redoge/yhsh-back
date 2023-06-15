package app.redoge.yhshback.service;


import app.redoge.yhshback.entity.Login;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.repository.LoginRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    public List<Login> getAllLoginByUserId(long id){
        return loginRepository.findAllByUserId(id);
    }
    public void addLogin(Login login){
        loginRepository.save(login);
        LOGGER.info("Login: " + login.getUser().getUsername());
    }
    public List<Login> getAllLogin(){
        LOGGER.debug("Get all login");
        return loginRepository.findAll();
    }

    public void addLoginByUser(User user) {
        var login = Login.builder()
                .user(user)
                .loginTime(LocalDateTime.now())
                .build();
        addLogin(login);
    }
}
