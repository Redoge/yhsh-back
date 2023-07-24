package app.redoge.yhshback.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@ToString
public class Activity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1905122041952221207L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private long id;

    @Column(name = "activity_name")
    private String name;

    @Column(name = "activity_notation")
    private String notation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("activities")
    private User creator;

    @OneToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("activity")
    private List<Training> trainings;

    @Column(name = "activity_is_removed")
    private boolean removed = false;

    public void addTraining(Training training) {
        if (isEmpty(trainings)) {
            trainings = new ArrayList<>();
        }
        trainings.add(training);
    }
}
