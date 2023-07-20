package app.redoge.yhshback.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @OneToMany
    @JsonIgnoreProperties("workouts")
    private List<Training> trainings;

    @ManyToOne
    @JsonIgnoreProperties("workouts")
    private User user;

    @Column(name = "workouts_start_time")
    private LocalDateTime startTime;

    @Column(name = "workouts_end_time")
    private LocalDateTime endTime;

    @Column(name = "workouts_removed")
    private boolean removed;
}
