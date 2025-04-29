package app.healthy.bot.service;

import app.healthy.bot.dto.GoalDto;
import app.healthy.bot.enums.Frecuency;
import app.healthy.bot.model.Goal;
import app.healthy.bot.model.Habit;
import app.healthy.bot.model.User;
import app.healthy.bot.repository.GoalRepository;
import app.healthy.bot.repository.HabitRepository;
import app.healthy.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository, HabitRepository habitRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    // Crear Goal
    public GoalDto createGoal(GoalDto dto) {
        // Crear una nueva entidad Goal
        Goal goal = new Goal();
        goal.setObjective(dto.getObjective());
        goal.setFrequency(Frecuency.valueOf(dto.getFrequency().name())); // Guardamos como Enum en la entidad
        goal.setStartDate(dto.getStartDate());
        goal.setEndDate(dto.getEndDate());

        // Buscar el usuario y asignarlo al Goal
        User user = userRepository.findById(dto.getUser_id())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        goal.setUser(user);

        // Buscar el hábito y asignarlo al Goal
        Habit habit = habitRepository.findById(dto.getHabit_id())
                .orElseThrow(() -> new RuntimeException("Hábito no encontrado"));
        goal.setHabit(habit);

        // Guardar el Goal en la base de datos
        Goal savedGoal = goalRepository.save(goal);

        // Mapear el Goal guardado de nuevo al GoalDto para devolverlo
        dto.setObjective(savedGoal.getObjective());
        dto.setFrequency(savedGoal.getFrequency());  // Aquí ya tenemos el Enum
        dto.setStartDate(savedGoal.getStartDate());
        dto.setEndDate(savedGoal.getEndDate());

        return dto;
    }

    // Obtener Goal por HabitId
    public GoalDto getGoalByHabitId(Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Hábito no encontrado"));

        // Encontrar el Goal asociado al Habit (relación uno a uno)
        Goal goal = goalRepository.findByHabit(habit)
                .orElseThrow(() -> new RuntimeException("Goal no encontrado para el hábito"));

        // Crear y llenar el DTO con los detalles del Goal
        GoalDto dto = new GoalDto();
        dto.setObjective(goal.getObjective());
        dto.setFrequency(goal.getFrequency()); // Directamente el Enum
        dto.setStartDate(goal.getStartDate());
        dto.setEndDate(goal.getEndDate());

        return dto;
    }

    // Actualizar Goal
    public GoalDto updateGoal(Long goalId, GoalDto goalDto) {
        // Buscar Goal por id
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal no encontrado"));

        // Actualizar los campos del Goal con los nuevos datos
        goal.setObjective(goalDto.getObjective());
        goal.setFrequency(Frecuency.valueOf(goalDto.getFrequency().name()));  // Guardamos como Enum
        goal.setStartDate(goalDto.getStartDate());
        goal.setEndDate(goalDto.getEndDate());

        // Guardar el Goal actualizado
        Goal updatedGoal = goalRepository.save(goal);

        // Mapear los cambios al DTO para devolver
        goalDto.setObjective(updatedGoal.getObjective());
        goalDto.setFrequency(updatedGoal.getFrequency());  // Aquí ya tenemos el Enum
        goalDto.setStartDate(updatedGoal.getStartDate());
        goalDto.setEndDate(updatedGoal.getEndDate());

        return goalDto;
    }

    // Eliminar Goal
    public void deleteGoal(Long goalId) {
        // Buscar Goal por id
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal no encontrado"));

        // Eliminar el Goal
        goalRepository.delete(goal);
    }
}
