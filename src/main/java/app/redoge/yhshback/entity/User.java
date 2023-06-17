package app.redoge.yhshback.entity;

import app.redoge.yhshback.entity.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "user_username")
    private String username;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private UserRole userRole;

    @Column(name = "user_sex")
    private String sex;

    @Column(name = "user_weight")
    private int weightKg;

    @Column(name = "user_height")
    private int heightSm;

    @OneToMany(mappedBy = "creator", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("creator")
    private List<Activity> activities;

    @Column(name = "user_enabled")
    @JsonIgnore
    private boolean enabled = true;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
