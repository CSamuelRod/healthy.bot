package app.healthy.bot.dto;

import app.healthy.bot.model.Habit;
import app.healthy.bot.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitDto {

    private Long habitId;
    private String name;
    private String description;
    private Long goalId;
    private Long userId;

    /**
     * Convierte una entidad Habit a un DTO HabitDto.
     *
     * @param habit entidad Habit
     * @return DTO HabitDto
     */
    public static HabitDto fromEntity(Habit habit) {
        HabitDto dto = new HabitDto();
        dto.setHabitId(habit.getHabitId());
        dto.setName(habit.getName());
        dto.setDescription(habit.getDescription());

        if (habit.getUser() != null) {
            dto.setUserId(habit.getUser().getUserId());
        }

        if (habit.getGoal() != null) {
            dto.setGoalId(habit.getGoal().getGoal_Id());
        }

        return dto;
    }

    /**
     * Convierte este DTO a una entidad Habit.
     *
     * @param user entidad User asociada
     * @return entidad Habit
     */
    public Habit toEntity(User user) {
        Habit habit = new Habit();
        habit.setHabitId(this.habitId);
        habit.setName(this.name);
        habit.setDescription(this.description);
        habit.setUser(user);
        return habit;
    }
}
