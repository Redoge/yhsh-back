package app.redoge.yhshback.service;

import app.redoge.yhshback.entity.ActivationCode;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.service.interfaces.IMailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class MailSenderService implements IMailSenderService {
    private final JavaMailSender mailSender;
    private final FileService fileService;
    private static final String FROM_EMAIL = "noreply@yhsh.me";
    private static final String SUBJECT_EMAIL = "YHSH - Email activation";
    private static final String TEXT_CONFIRMATION_EMAIL = "Dear %s, to confirm your account, please enter the following code - %s . Good bye!";
    private static final String TEXT_SUCCESSFUL_EMAIL = "Dear %s, you successful confirm your account!!!";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Override
    public boolean sendActivationCode(String to, ActivationCode activationCode) throws MessagingException, IOException {
        var message = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, true, "utf-8");
        message.setSubject(SUBJECT_EMAIL);
        helper.setFrom(FROM_EMAIL);
        helper.setTo(to);
        var htmlContent = fileService.readResourceFile("/mail/templates/activation.html");
        htmlContent = format(htmlContent, activationCode.getUser().getUsername(), activationCode.getCode(), activationCode.getCode());
        helper.setText(htmlContent, true);
        return sendMimeMail(message);
    }
    @Override
    public void sendMessageOfSuccessfulConfirmation(User user) {
        var message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(user.getEmail());
        message.setSubject(SUBJECT_EMAIL);
        message.setText(format(TEXT_SUCCESSFUL_EMAIL, user.getUsername()));
        sendSimpleMail(message);
    }
    private boolean sendSimpleMail(SimpleMailMessage message){
        return executorService.submit(() -> mailSender.send(message) , true).isDone();
    }
    private boolean sendMimeMail(MimeMessage message){
        return executorService.submit(() -> mailSender.send(message) , true).isDone();
    }

}
