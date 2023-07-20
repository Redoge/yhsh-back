package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;

public interface IUserEmailConfirmationService {
    boolean sendEmailConfirmation(User user) throws BadRequestException;
    boolean confirmedUserByCode(String code) throws NotFoundException, BadRequestException;
    void sendCodeByEmail(String email) throws NotFoundException, BadRequestException;
}
