package app.redoge.yhshback.config;

import app.redoge.yhshback.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity()
public class SecurityConfig {
    private static final String ALL_PATH = "/**";
    private static final String ALL_ELEMENTS = "*";
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(this::corsConfiguration)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ADMIN_PATH.concat("**"))
                        .hasAuthority(UserRole.ADMIN.name())
                        .requestMatchers(ACTIVATION_PATH.concat("/**"))
                        .permitAll()
                        .requestMatchers(
                                USERS_PATH.concat(ALL_PATH),
                                LOGINS_PATH.concat(ALL_PATH),
                                TRAININGS_PATH.concat(ALL_PATH),
                                ACTIVITIES_PATH.concat(ALL_PATH))
                        .hasAnyAuthority(
                                UserRole.USER.name(),
                                UserRole.ADMIN.name())
                        .requestMatchers(AUTH_PATH.concat(ALL_PATH))
                        .permitAll()
                )
                .sessionManagement(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private void corsConfiguration(CorsConfigurer<HttpSecurity> request){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(ALL_ELEMENTS));
        configuration.setAllowedMethods(List.of(ALL_ELEMENTS));
        configuration.setAllowedHeaders(List.of(ALL_ELEMENTS));
        configuration.setExposedHeaders(List.of(ALL_ELEMENTS));
        configuration.setAllowedOriginPatterns(List.of(ALL_ELEMENTS));
        configuration.setMaxAge(3600L);
        final var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(ALL_PATH, configuration);
        request.configurationSource(source);
    }
}

