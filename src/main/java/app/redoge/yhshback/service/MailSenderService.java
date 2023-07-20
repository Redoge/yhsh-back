package app.redoge.yhshback.service;

import app.redoge.yhshback.entity.ActivationCode;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.service.interfaces.IMailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class MailSenderService implements IMailSenderService {
    private final MailSender mailSender;
    private static final String FROM_EMAIL = "noreply@yhsh.app";
    private static final String SUBJECT_EMAIL = "Activation account";
    private static final String TEXT_CONFIRMATION_EMAIL = "Dear %s, to confirm your account, please enter the following code - %s . Good bye!";
    private static final String TEXT_SUCCESSFUL_EMAIL = "Dear %s, you successful confirm your account!!!";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Override
    public boolean sendActivationCode(String to, ActivationCode activationCode) {
        var message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(to);
        message.setSubject(SUBJECT_EMAIL);
        message.setText(format(TEXT_CONFIRMATION_EMAIL, activationCode.getUser().getUsername(), activationCode.getCode()));
        return sendMail(message);
    }
    @Override
    public void sendMessageOfSuccessfulConfirmation(User user) {
        var message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(user.getEmail());
        message.setSubject(SUBJECT_EMAIL);
        message.setText(format(TEXT_SUCCESSFUL_EMAIL, user.getUsername()));
        sendMail(message);
    }
    private boolean sendMail(SimpleMailMessage message){
        return executorService.submit(() -> mailSender.send(message) , true).isDone();
    }


}
