package app.redoge.yhshback.service.interfaces;

import app.redoge.yhshback.dto.AuthenticationRequestDto;
import app.redoge.yhshback.dto.response.AuthenticationResponseDto;
import app.redoge.yhshback.dto.RegisterRequestDto;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.UserIsExistException;

public interface IAuthService {
    AuthenticationResponseDto registerUser(RegisterRequestDto request) throws UserIsExistException, BadRequestException;
    AuthenticationResponseDto registerAdmin(RegisterRequestDto request) throws UserIsExistException, BadRequestException;
    AuthenticationResponseDto auth(AuthenticationRequestDto request) throws BadRequestException;
    boolean userExistsByUsername(String username);
}
