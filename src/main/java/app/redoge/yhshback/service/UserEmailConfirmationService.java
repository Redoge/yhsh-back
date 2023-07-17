package app.redoge.yhshback.service;

import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserEmailConfirmationService {
    private final ActivationCodeService activationCodeService;
    private final MailSenderService mailSenderService;
    private final UserService userService;


    public boolean sendEmailConfirmation(User user) throws BadRequestException {
        var code = activationCodeService.saveByUser(user);
        return mailSenderService.sendActivationCode(user.getEmail(), code);
    }

    public boolean confirmedUserByCode(String code) throws NotFoundException, BadRequestException {
        var user = activationCodeService.getUserByCodeAndRemoveCode(code);
        var res = userService.confirmEmail(user);
        sendMessageOfSuccessfulConfirmation(user);
        return res;
    }

    private void sendMessageOfSuccessfulConfirmation(User user) {
        mailSenderService.sendMessageOfSuccessfulConfirmation(user);
    }

    public void sendCodeByEmail(String email) throws NotFoundException, BadRequestException {
        var user = userService.getUserByEmail(email);
        if(user.isEmailConfirmed())
            throw new BadRequestException("Email is already confirmed!");
        activationCodeService.removeByUser(user);
        sendEmailConfirmation(user);
    }
}
