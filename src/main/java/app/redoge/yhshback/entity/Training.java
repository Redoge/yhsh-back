package app.redoge.yhshback.entity;

import app.redoge.yhshback.entity.enums.TrainingMode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity(name = "trainings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Training implements Serializable {
    @Serial
    private static final long serialVersionUID = 1905122041953331207L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "training_id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"trainings", "creator"})
    private Activity activity;

    @Column(name = "training_count")
    private int count;

    @Column(name = "training_start_time")
    private LocalDateTime startTime;

    @Column(name = "training_is_removed")
    private boolean removed = false;

    @Column(name = "training_mode")
    @Enumerated(EnumType.STRING)
    private TrainingMode mode;
}
