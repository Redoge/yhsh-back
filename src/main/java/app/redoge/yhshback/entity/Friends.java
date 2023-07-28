package app.redoge.yhshback.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "friends")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friends_id")
    private long id;
    @ManyToOne
    private User user1;
    @ManyToOne
    private User user2;
}
