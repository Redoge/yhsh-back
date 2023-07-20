package app.redoge.yhshback.controller;

import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.service.interfaces.IUserEmailConfirmationService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static app.redoge.yhshback.utill.paths.Constants.ACTIVATION_PATH;


@RestController
@RequestMapping(ACTIVATION_PATH)
@AllArgsConstructor
public class ActivationCodeController {
    private final IUserEmailConfirmationService userEmailConfirmationService;
    @GetMapping
    public String activateUser(@RequestParam(value = "token") String token) throws NotFoundException, BadRequestException {
        userEmailConfirmationService.confirmedUserByCode(token);
        return "Account confirmed!!!";
    }
    @GetMapping("/resend")
    public String resendCode(@RequestParam(value = "email") String email) throws NotFoundException, BadRequestException {
        userEmailConfirmationService.sendCodeByEmail(email);
        return "Activation code resend!!!";
    }
}
