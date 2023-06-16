package app.redoge.yhshback.entity;

import app.redoge.yhshback.entity.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "user_username")
    private String username;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Enumerated(EnumType.STRING)
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
    private boolean enabled = true;
}
