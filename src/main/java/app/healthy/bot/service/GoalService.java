package app.healthy.bot.service;

import app.healthy.bot.dto.GoalDto;
import app.healthy.bot.enums.Frecuency;
import app.healthy.bot.model.Goal;
import app.healthy.bot.model.Habit;
import app.healthy.bot.repository.GoalRepository;
import app.healthy.bot.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar metas (goals) asociadas a hábitos.
 */
@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final HabitRepository habitRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository, HabitRepository habitRepository) {
        this.goalRepository = goalRepository;
        this.habitRepository = habitRepository;
    }

    /**
     * Crea una nueva meta (goal) asociada a un hábito.
     *
     * @param dto Datos de la meta a crear
     * @return DTO con los datos de la meta guardada
     */
    public GoalDto createGoal(GoalDto dto) {
        Goal goal = dto.toEntity();

        Habit habit = habitRepository.findById(dto.getHabit_id())
                .orElseThrow(() -> new RuntimeException("Hábito no encontrado"));
        goal.setHabit(habit);

        Goal savedGoal = goalRepository.save(goal);
        return GoalDto.fromEntity(savedGoal);
    }

    /**
     * Obtiene la meta (goal) asociada a un hábito.
     *
     * @param habitId ID del hábito
     * @return DTO con los datos de la meta
     */
    public GoalDto getGoalByHabitId(Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Hábito no encontrado"));

        Goal goal = goalRepository.findByHabit(habit)
                .orElseThrow(() -> new RuntimeException("Meta no encontrada para el hábito"));

        return GoalDto.fromEntity(goal);
    }

    /**
     * Actualiza una meta existente.
     *
     * @param goalId ID de la meta a actualizar
     * @param goalDto DTO con los nuevos datos de la meta
     * @return DTO actualizado
     */
    public GoalDto updateGoal(Long goalId, GoalDto goalDto) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Meta no encontrada"));

        goal.setObjective(goalDto.getObjective());
        goal.setFrequency(Frecuency.valueOf(goalDto.getFrequency().name()));
        goal.setStartDate(goalDto.getStartDate());
        goal.setEndDate(goalDto.getEndDate());

        Goal updatedGoal = goalRepository.save(goal);
        return GoalDto.fromEntity(updatedGoal);
    }

    /**
     * Elimina una meta por su ID.
     *
     * @param goalId ID de la meta a eliminar
     */
    public void deleteGoal(Long goalId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Meta no encontrada"));

        goalRepository.delete(goal);
    }
}
