package app.redoge.yhshback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity(name = "trainings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Activity activity;

    @Column(name = "training_count")
    private int count;

    @Column(name = "training_start_time")
    private LocalDateTime startTime;

    @Column(name = "training_end_time")
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(name = "training_is_removed")
    private boolean removed = false;

}
