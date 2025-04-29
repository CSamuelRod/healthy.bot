package app.healthy.bot.dto;

import app.healthy.bot.enums.Frecuency;
import app.healthy.bot.model.Habit;
import app.healthy.bot.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalDto {

    private Long user_id;

    private Long habit_id;

    private String objective;

    private Frecuency frequency;

    private LocalDate startDate;

    private LocalDate endDate;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getHabit_id() {
        return habit_id;
    }

    public void setHabit_id(Long habit_id) {
        this.habit_id = habit_id;
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
}
