package app.redoge.yhshback.service;


import app.redoge.yhshback.dto.AuthenticationRequestDto;
import app.redoge.yhshback.dto.AuthenticationResponseDto;
import app.redoge.yhshback.dto.RegisterRequestDto;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.enums.UserRole;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.UserIsExistException;
import app.redoge.yhshback.repository.UserRepository;
import app.redoge.yhshback.utill.validators.DtoValidators;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final DtoValidators dtoValidators;

    public AuthenticationResponseDto registerUser(RegisterRequestDto request) throws UserIsExistException, BadRequestException {
        return registerByRequestDtoAndRole(request, UserRole.USER);
    }
    public AuthenticationResponseDto registerAdmin(RegisterRequestDto request) throws UserIsExistException, BadRequestException {
        return registerByRequestDtoAndRole(request, UserRole.ADMIN);
    }


    public AuthenticationResponseDto auth(AuthenticationRequestDto request) throws BadRequestException {
        if(!dtoValidators.authenticationRequestDtoIsValid(request))
            throw new BadRequestException("Invalid credentials!!!");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(),
                        request.password()));
        var user = repository.findByUsername(request.username()).orElseThrow();
        var jwt = jwtService.generateToken(user);
        return new AuthenticationResponseDto(jwt);
    }
    public void saveUser(User user) {
        if(isNotEmpty(user)) {
            save(user, UserRole.USER);
        }
    }
    public void saveAdmin(User user) {
        if(isNotEmpty(user)) {
            save(user, UserRole.ADMIN);
        }
    }
    public void save(User user, UserRole userRole) {
        if(isNotEmpty(user.getUsername()) && isNotEmpty(user.getEmail()) && isNotEmpty(user.getPassword()) &&
                !userExistsByEmail(user.getEmail()) && !userExistsByUsername(user.getUsername())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUserRole(userRole);
            repository.save(user);
        }
    }
    public boolean userExistsByUsername(String username) {
        return repository.existsByUsername(username);
    }
    public boolean userExistsByEmail(String email) {
        return repository.existsByEmail(email);
    }
    private AuthenticationResponseDto registerByRequestDtoAndRole(RegisterRequestDto request, UserRole userRole) throws UserIsExistException, BadRequestException {
        if(!dtoValidators.registerRequestDtoIsValid(request))
            throw new BadRequestException("Bad request!!!");

        if(repository.existsByUsernameOrEmail(request.username(), request.email()))
            throw new UserIsExistException(request.username(), request.email());
        var user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .userRole(userRole)
                .email(request.email())
                .enabled(true)
                .build();
        repository.save(user);
        var jwt = jwtService.generateToken(user);
        return new AuthenticationResponseDto(jwt);
    }
}
