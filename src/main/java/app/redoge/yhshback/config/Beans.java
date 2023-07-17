package app.redoge.yhshback.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Properties;

@Configuration
@AllArgsConstructor
public class Beans {

    private final UserDetailsService userDetailsService;
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(encoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Bean
    public MailSender mailSender() {
        var mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.email.eu-frankfurt-1.oci.oraclecloud.com");
        mailSender.setPort(587);
        mailSender.setUsername("ocid1.user.oc1..aaaaaaaahsmbyqefruzdnxslp3objgjygtycbalnphzb6sj57fmihh2qheva@ocid1.tenancy.oc1..aaaaaaaae3234swttg7duu5cwqde6llxsqfashuhlh43uzfahci3wfx455ia.v2.com");
        mailSender.setPassword(">3.6Eb.Niba9L8Xx>H0p");
        mailSender.setJavaMailProperties(new Properties());
        mailSender.getJavaMailProperties().put("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }
    @Bean
    public SimpleMailMessage simpleMailMessage() {
        return new SimpleMailMessage();
    }
}
