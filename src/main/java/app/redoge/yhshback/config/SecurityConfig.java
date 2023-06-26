package app.redoge.yhshback.config;

import app.redoge.yhshback.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static app.redoge.yhshback.utill.paths.Constants.*;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private static final String allPath = "/**";
    private static final String allElements = "*";
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(this::corsConfiguration)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                USERS_PATH.concat(allPath),
                                LOGINS_PATH.concat(allPath),
                                TRAININGS_PATH.concat(allPath),
                                ACTIVITIES_PATH.concat(allPath))
                        .hasAnyAuthority(
                                UserRole.USER.name(),
                                UserRole.ADMIN.name())
                        .requestMatchers(AUTH_PATH.concat(allPath))
                        .permitAll()
                )
                .sessionManagement(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private void corsConfiguration(CorsConfigurer<HttpSecurity> request){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allElements));
        configuration.setAllowedMethods(List.of(allElements));
        configuration.setAllowedHeaders(List.of(allElements));
        configuration.setExposedHeaders(List.of(allElements));
        configuration.setAllowedOriginPatterns(List.of(allElements));
        configuration.setMaxAge(3600L);
        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(allPath, configuration);
        request.configurationSource(source);
    }
}

