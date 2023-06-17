package app.redoge.yhshback.controller;

import app.redoge.yhshback.dto.AuthenticationRequestDto;
import app.redoge.yhshback.dto.AuthenticationResponseDto;
import app.redoge.yhshback.dto.RegisterRequestDto;
import app.redoge.yhshback.exception.BadRequestException;
import app.redoge.yhshback.exception.UserIsExistException;
import app.redoge.yhshback.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static app.redoge.yhshback.utill.paths.Constants.AUTH_PATH;


@RestController
@RequestMapping(AUTH_PATH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto request) throws UserIsExistException, BadRequestException {
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> auth(@RequestBody AuthenticationRequestDto request) throws BadRequestException {
        return ResponseEntity.ok(authService.auth(request));
    }
}
