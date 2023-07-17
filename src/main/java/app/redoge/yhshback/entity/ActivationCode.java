package app.redoge.yhshback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "activation_code")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ActivationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activation_code_id")
    private long id;
    @Column(name = "activation_code")
    private String code;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @Column(name = "activation_code_created_time")
    private LocalDateTime createdTime;
}
