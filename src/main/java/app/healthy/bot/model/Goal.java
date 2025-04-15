package app.healthy.bot.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "goals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goal_Id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    private String objective;

    private String frequency; // "Daily", "Weekly", etc.

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL)
    private List<Progress> progresses;
}