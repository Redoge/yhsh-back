package app.redoge.yhshback.service.interfaces;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface IJwtService {
    String extractUsername(String jwt);

    <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails);

    String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    );

    boolean isTokenValid(String jwt, UserDetails user);
}
