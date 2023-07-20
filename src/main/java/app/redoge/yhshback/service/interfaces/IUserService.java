package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.dto.UserUpdateRequestDto;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.enums.UserRole;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.NotFoundException;
import app.redoge.yhshback.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface IUserService extends UserDetailsService {
    User findUserByUsername(String username) throws UserNotFoundException;

    User getUserByEmail(String email) throws NotFoundException;

    List<User> getAllUsers();

    List<User> getAllUsersByUserRole(UserRole userRole);

    void changeUserRoleByUserId(Long userId) throws BadRequestException, UserNotFoundException;

    void changeEnabledByUserId(Long userId) throws BadRequestException, UserNotFoundException;

    User getUserById(long id) throws UserNotFoundException;

    User getUserByUsername(String username);

    User updateUserByUserUpdateRequest(UserUpdateRequestDto userUpdateRequest) throws BadRequestException;

    List<User> getAllUsersByEnabled(boolean filter);

    List<User> getUsersByFilter(String param, String value) throws NotFoundException;

    boolean confirmEmail(User user);
}
