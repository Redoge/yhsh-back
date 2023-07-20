package app.redoge.yhshback.service;


import app.redoge.yhshback.dto.AuthenticationRequestDto;
import app.redoge.yhshback.dto.AuthenticationResponseDto;
import app.redoge.yhshback.dto.RegisterRequestDto;
import app.redoge.yhshback.entity.User;
import app.redoge.yhshback.entity.enums.UserRole;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.UserIsExistException;
import app.redoge.yhshback.repository.UserRepository;
import app.redoge.yhshback.service.interfaces.IAuthService;
import app.redoge.yhshback.service.interfaces.IJwtService;
import app.redoge.yhshback.service.interfaces.ILoginService;
import app.redoge.yhshback.service.interfaces.IUserEmailConfirmationService;
import app.redoge.yhshback.utill.validators.DtoValidators;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import static java.lang.String.format;


@Service
@AllArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService jwtService;
    private final ILoginService loginService;
    private final AuthenticationManager authenticationManager;
    private final DtoValidators dtoValidators;
    private final IUserEmailConfirmationService userEmailConfirmationService;

    @Transactional
    @Override
    public AuthenticationResponseDto registerUser(RegisterRequestDto request) throws UserIsExistException, BadRequestException {
        return registerByRequestDtoAndRole(request, UserRole.USER);
    }
    @Transactional
    @Override
    public AuthenticationResponseDto registerAdmin(RegisterRequestDto request) throws UserIsExistException, BadRequestException {
        return registerByRequestDtoAndRole(request, UserRole.ADMIN);
    }
    @Override
    public AuthenticationResponseDto auth(AuthenticationRequestDto request) throws BadRequestException {
        if(!dtoValidators.authenticationRequestDtoIsValid(request))
            throw new BadRequestException("Invalid credentials!!!");

        var user = repository.findByUsernameOrEmail(request.username(), request.username()).
                orElseThrow(()-> new UsernameNotFoundException(format("User with username or email '%s' not found", request.username())));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), request.password()));
        var jwt = jwtService.generateToken(user);
        return new AuthenticationResponseDto(jwt);
    }
    @Override
    public boolean userExistsByUsername(String username) {
        return repository.existsByUsername(username);
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
                .emailConfirmed(false)
                .build();
        repository.save(user);
        userEmailConfirmationService.sendEmailConfirmation(user);
        var jwt = jwtService.generateToken(user);
        return new AuthenticationResponseDto(jwt);
    }
}
