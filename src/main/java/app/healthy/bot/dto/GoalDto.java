package app.healthy.bot.dto;

import app.healthy.bot.enums.Frecuency;
import app.healthy.bot.model.Goal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalDto {
    private Long goalId;
    private Long habit_id;
    private String objective;
    private Frecuency frequency;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Convierte una entidad Goal a GoalDto.
     *
     * @param goal Entidad Goal
     * @return DTO correspondiente
     */
    public static GoalDto fromEntity(Goal goal) {
        GoalDto dto = new GoalDto();
        dto.setGoalId(goal.getGoal_Id());
        if (goal.getHabit() != null) {
            dto.setHabit_id(goal.getHabit().getHabitId());
        }
        dto.setObjective(goal.getObjective());
        dto.setFrequency(goal.getFrequency());
        dto.setStartDate(goal.getStartDate());
        dto.setEndDate(goal.getEndDate());
        return dto;
    }

    /**
     * Convierte este DTO en una entidad Goal (sin hábito).
     *
     * @return Entidad Goal correspondiente
     */
    public Goal toEntity() {
        Goal goal = new Goal();
        goal.setObjective(this.objective);
        goal.setFrequency(this.frequency);
        goal.setStartDate(this.startDate);
        goal.setEndDate(this.endDate);
        return goal;
    }
}
