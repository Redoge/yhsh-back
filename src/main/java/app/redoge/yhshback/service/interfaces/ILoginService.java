package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.entity.Login;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.NotFoundException;

import java.util.List;

public interface ILoginService {
    List<Login> getAllLoginByUserId(long userId);

    void addLogin(Login login);

    List<Login> getAllLogin();

    void addLoginByUser(User user);

    Login getById(long id) throws NotFoundException;

    List<Login> getAllLoginByUserUsername(String username);
}
