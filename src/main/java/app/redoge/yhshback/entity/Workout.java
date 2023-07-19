package app.redoge.yhshback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "workouts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workouts_id")
    private Long id;

    @ManyToMany
    private List<Training> trainings;

    @ManyToOne
    private User user;

    @Column(name = "workouts_time")
    private LocalDateTime time;

    @Column(name = "workouts_removed")
    private boolean removed;
}
