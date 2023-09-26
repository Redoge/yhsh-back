package app.redoge.yhshback.config;

import app.redoge.yhshback.dto.RegisterRequestDto;
import app.redoge.yhshback.entity.ActivityType;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.UserIsExistException;
import app.redoge.yhshback.service.interfaces.IActivityType;
import app.redoge.yhshback.service.interfaces.IAuthService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class StartConfig {
    private final IAuthService authService;
    private final IActivityType activityTypeService;

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

    @PostConstruct
    public void addDefaultActivityTypesIfNotExist(){
        if(activityTypeService.getById(1).isEmpty()){
            var activityTypeTime  = ActivityType.builder().name("Time").notation("sec").build();
            var activityTypeQuantity  = ActivityType.builder().name("Quantity").notation("times").build();
            var activityTypeDistance  = ActivityType.builder().name("Distance").notation("meters").build();
            var allActivityTypes = List.of(activityTypeTime, activityTypeQuantity, activityTypeDistance);
            activityTypeService.saveAll(allActivityTypes);
        }
    }
}
