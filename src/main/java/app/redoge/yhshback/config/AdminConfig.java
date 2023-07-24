package app.redoge.yhshback.config;

import app.redoge.yhshback.dto.RegisterRequestDto;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.UserIsExistException;
import app.redoge.yhshback.service.interfaces.IAuthService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AdminConfig {
    private final IAuthService authService;

    @Value("${yhsh.admin.username}")
    private String adminUsername;

    @Value("${yhsh.admin.password}")
    private String adminPassword;

    @Value("${yhsh.admin.email}")
    private String adminEmail;

    @PostConstruct
    public void addAdminIfNotExist() throws BadRequestException, UserIsExistException {
        boolean adminIsExist = authService.userExistsByUsername(adminUsername);
        if(!adminIsExist){
            var registerDto = new RegisterRequestDto(adminUsername, adminEmail, adminPassword);
            authService.registerAdmin(registerDto);
        }
    }
}
