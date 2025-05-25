package app.healthy.bot.model;

import app.healthy.bot.enums.Frecuency;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "habit_id", unique = true)
    private Habit habit;

    private String objective;

    @Enumerated(EnumType.STRING)
    private Frecuency frequency;

    private LocalDate startDate;

    private LocalDate endDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "goal", cascade = CascadeType.REMOVE)
    private List<Progress> progresses;


}