package app.redoge.yhshback.config;

import app.redoge.yhshback.dto.RegisterRequestDto;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.UserIsExistException;
import app.redoge.yhshback.service.interfaces.IAuthService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class AdminConfig {
    private final IAuthService authService;
    @PostConstruct
    public void addAdminIfNotExist() throws BadRequestException, UserIsExistException {
        boolean adminIsExist = authService.userExistsByUsername("Redoge");
        if(!adminIsExist){
            var registerDto = new RegisterRequestDto("Redoge", "mail.redoge@gmail.com", "redoge1670");
            authService.registerAdmin(registerDto);
        }
    }
}
