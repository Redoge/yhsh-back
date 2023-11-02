package app.redoge.yhshback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;


@Entity(name = "user_weights")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserWeight {
    @Id
    @Column(name = "user_weights_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_weights_weight")
    private Float weight;
    @Column(name = "user_weights_date")
    private Date date;
}
