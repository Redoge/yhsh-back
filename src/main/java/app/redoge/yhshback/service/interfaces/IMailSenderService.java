package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.entity.ActivationCode;
import app.redoge.yhshback.entity.User;


public interface IMailSenderService {
    boolean sendActivationCode(String to, ActivationCode activationCode);
    void sendMessageOfSuccessfulConfirmation(User user);
}
