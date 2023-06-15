package app.redoge.yhshback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "activities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private long id;

    @Column(name = "activity_name")
    private String name;

    @Column(name = "activity_notation")
    private String notation;

    @ManyToOne(fetch = FetchType.EAGER)
    private User creator;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Training> trainings;

    @Column(name = "activity_is_removed")
    private boolean removed = false;

    public void addTraining(Training training) {
        if (trainings == null) {
            trainings = new ArrayList<>();
        }
        trainings.add(training);
    }
}
