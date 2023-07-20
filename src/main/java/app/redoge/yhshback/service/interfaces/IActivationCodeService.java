package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.entity.ActivationCode;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;


public interface IActivationCodeService {
    User getUserByCodeAndRemoveCode(String code) throws NotFoundException, BadRequestException;
    void removeByUser(User user);
    ActivationCode saveByUser(User user) throws BadRequestException;
}
