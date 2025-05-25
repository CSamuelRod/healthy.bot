package app.healthy.bot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "habits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long habitId;

    private String name;

    private String description;

    @JsonBackReference
    @OneToOne(mappedBy = "habit", cascade = CascadeType.REMOVE)
    private Goal goal;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference("goal-user")
    private User user;

}
