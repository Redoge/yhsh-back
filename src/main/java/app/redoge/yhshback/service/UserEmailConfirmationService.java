package app.redoge.yhshback.service;

import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.service.interfaces.IActivationCodeService;
import app.redoge.yhshback.service.interfaces.IMailSenderService;
import app.redoge.yhshback.service.interfaces.IUserEmailConfirmationService;
import app.redoge.yhshback.service.interfaces.IUserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class UserEmailConfirmationService implements IUserEmailConfirmationService {
    private final IActivationCodeService activationCodeService;
    private final IMailSenderService mailSenderService;
    private final IUserService userService;

    @Override
    public boolean sendEmailConfirmation(User user) throws BadRequestException{
        try{
            var code = activationCodeService.saveByUser(user);
            return mailSenderService.sendActivationCode(user.getEmail(), code);
        }catch (MessagingException | IOException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    @Override
    public boolean confirmedUserByCode(String code) throws NotFoundException, BadRequestException {
        var user = activationCodeService.getUserByCodeAndRemoveCode(code);
        var res = userService.confirmEmail(user);
        sendMessageOfSuccessfulConfirmation(user);
        return res;
    }
    private void sendMessageOfSuccessfulConfirmation(User user) {
        mailSenderService.sendMessageOfSuccessfulConfirmation(user);
    }
    @Override
    public void sendCodeByEmail(String email) throws NotFoundException, BadRequestException{
        var user = userService.getUserByEmail(email);
        if(user.isEmailConfirmed())
            throw new BadRequestException("Email is already confirmed!");
        activationCodeService.removeByUser(user);
        sendEmailConfirmation(user);
    }
}
