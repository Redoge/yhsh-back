package app.redoge.yhshback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Entity(name = "activities")
@Data
@EqualsAndHashCode(exclude = "trainings")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1905122041952221207L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private long id;

    @Column(name = "activity_name", length = 50)
    private String name;

    @Column(name = "activity_description")
    private String description;

    @Column(name = "activity_with_weight")
    private boolean withWeight;

    @ManyToOne
    private ActivityType type;

    @ManyToOne(fetch = FetchType.EAGER)
    private User creator;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Training> trainings;

    @Column(name = "activity_is_removed")
    @Builder.Default
    private boolean removed = false;

    public void addTraining(Training training) {
        if (isEmpty(trainings)) {
            trainings = new ArrayList<>();
        }
        trainings.add(training);
    }
}
