package app.redoge.yhshback.config;

import app.redoge.yhshback.dto.RegisterRequestDto;
import app.redoge.yhshback.entity.ActivityType;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.exception.UserIsExistException;
import app.redoge.yhshback.service.interfaces.IActivityTypeService;
import app.redoge.yhshback.service.interfaces.IAuthService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class StartConfig {
    private final IAuthService authService;
    private final IActivityTypeService activityTypeService;

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
            log.info("Added admin - " + adminUsername);
        }
    }

    @PostConstruct
    public void addDefaultActivityTypesIfNotExist(){
        try{
            activityTypeService.getById(1);
        }catch (NotFoundException e){
            var activityTypeTime  = ActivityType.builder().name("Time").notation("sec").build();
            var activityTypeQuantity  = ActivityType.builder().name("Quantity").notation("times").build();
            var activityTypeDistance  = ActivityType.builder().name("Distance").notation("meters").build();
            var allActivityTypes = List.of(activityTypeTime, activityTypeQuantity, activityTypeDistance);
            activityTypeService.saveAll(allActivityTypes);
            log.info("Added activity types - " + allActivityTypes.stream().map(ActivityType::getName).toList());
        }
    }
}
