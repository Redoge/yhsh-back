package app.redoge.yhshback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "friendships")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendships_id")
    private long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    @Column(name = "friendships_created")
    private LocalDateTime created;

}
