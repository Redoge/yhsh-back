package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.entity.ActivationCode;
import app.redoge.yhshback.entity.User;
import jakarta.mail.MessagingException;

import java.io.IOException;


public interface IMailSenderService {
    boolean sendActivationCode(String to, ActivationCode activationCode) throws MessagingException, IOException;
    void sendMessageOfSuccessfulConfirmation(User user);
}
