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

    @JsonManagedReference("goal-user")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "habit_id", unique = true) // ⚡ Muy importante: unique=true
    private Habit habit;

    private String objective;

    @Enumerated(EnumType.STRING)
    private Frecuency frequency;

    private LocalDate startDate;

    private LocalDate endDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "goal", cascade = CascadeType.REMOVE)
    private List<Progress> progresses;

    public Long getGoal_Id() {
        return goal_Id;
    }

    public void setGoal_Id(Long goal_Id) {
        this.goal_Id = goal_Id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public Frecuency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frecuency frequency) {
        this.frequency = frequency;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Progress> getProgresses() {
        return progresses;
    }

    public void setProgresses(List<Progress> progresses) {
        this.progresses = progresses;
    }
}