package app.redoge.yhshback.service;

import app.redoge.yhshback.entity.ActivationCode;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.repository.ActivationCodeRepository;
import app.redoge.yhshback.utill.StringTokenGenerator;
import app.redoge.yhshback.utill.validators.ActivationCodeValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class ActivationCodeService {
    private final ActivationCodeRepository repository;
    private final StringTokenGenerator tokenGenerator;
    private final ActivationCodeValidator activationCodeValidator;
    public User getUserByCodeAndRemoveCode(String code) throws NotFoundException, BadRequestException {
        var user = getByCode(code).getUser();
        removeActivationCodeByCode(code);
        return user;
    }
    public void removeByUser(User user){
        repository.removeByUser(user);
    }
    public ActivationCode saveByUser(User user) throws BadRequestException {
        if(isEmpty(user))
            throw new BadRequestException("Activation code not created!! User cannot be empty!");
        var token = tokenGenerator.getToken(32);
        var activationCode = ActivationCode
                .builder()
                .code(token)
                .user(user)
                .createdTime(LocalDateTime.now())
                .build();
        return repository.save(activationCode);
    }
    private void removeActivationCodeByCode(String code){
        repository.removeByCode(code);
    }
    private ActivationCode getByCode(String token) throws NotFoundException, BadRequestException {
        var code = repository.findByCode(token).orElseThrow(() -> new NotFoundException("Activation code " + token + " not found"));
        if(activationCodeValidator.isValid(code)) return code;
        throw new BadRequestException("Activation code is expired!!!");
    }
}
