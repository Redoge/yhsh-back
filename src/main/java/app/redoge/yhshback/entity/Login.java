package app.redoge.yhshback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity(name="login")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(name = "login_time")
    private LocalDateTime loginTime;

}
