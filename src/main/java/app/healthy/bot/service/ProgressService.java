package app.healthy.bot.service;

import app.healthy.bot.dto.ProgressDto;
import app.healthy.bot.dto.ProgressPercentageDto;
import app.healthy.bot.enums.Frecuency;
import app.healthy.bot.model.Goal;
import app.healthy.bot.model.Habit;
import app.healthy.bot.model.Progress;
import app.healthy.bot.model.User;
import app.healthy.bot.repository.GoalRepository;
import app.healthy.bot.repository.HabitRepository;
import app.healthy.bot.repository.ProgressRepository;
import app.healthy.bot.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona el progreso de los hábitos asociados a metas de los usuarios.
 * Proporciona funcionalidades para guardar registros de progreso, obtener progreso por fecha,
 * calcular porcentajes de cumplimiento mensual, y eliminar registros.
 */
@Service
public class ProgressService {

    private final UserRepository userRepository;
    private final ProgressRepository progressRepository;
    private final GoalRepository goalRepository;
    private final HabitRepository habitRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param userRepository     Repositorio de usuarios
     * @param progressRepository Repositorio de progreso
     * @param goalRepository     Repositorio de metas
     * @param habitRepository    Repositorio de hábitos
     */
    public ProgressService(UserRepository userRepository,
                           ProgressRepository progressRepository,
                           GoalRepository goalRepository,
                           HabitRepository habitRepository) {
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
        this.goalRepository = goalRepository;
        this.habitRepository = habitRepository;
    }

    /**
     * Guarda un nuevo registro de progreso asociado a una meta.
     *
     * @param progressDto Datos del progreso a guardar
     * @return DTO del progreso guardado
     */
    public ProgressDto save(ProgressDto progressDto) {
        Goal goal = goalRepository.findById(progressDto.getGoalId())
                .orElseThrow(() -> new RuntimeException("Goal no encontrado con ID: " + progressDto.getGoalId()));

        Progress progress = new Progress();
        progress.setGoal(goal);
        progress.setDate(progressDto.getDate());
        progress.setCompleted(progressDto.getCompleted());
        progress.setNotes(progressDto.getNotes());

        Progress saved = progressRepository.save(progress);

        return ProgressDto.fromEntity(saved);
    }

    /**
     * Obtiene los registros de progreso únicos por usuario y fecha.
     *
     * @param userId ID del usuario
     * @param date   Fecha del progreso
     * @return Lista de DTOs de progreso encontrados
     */
    public List<ProgressDto> getUniqueProgressByUserAndDate(Long userId, LocalDate date) {
        List<Progress> progresses = progressRepository.findUniqueProgress(userId, date);
        List<ProgressDto> dtoList = new ArrayList<>();
        for (Progress progress : progresses) {
            dtoList.add(ProgressDto.fromEntity(progress));
        }
        return dtoList;
    }
    /**
     * Elimina un registro de progreso por su ID.
     *
     * @param id ID del progreso
     */
    public void delete(Long id) {
        progressRepository.deleteById(id);
    }

    /**
     * Calcula el porcentaje de cumplimiento mensual de cada hábito del usuario.
     *
     * @param userId ID del usuario
     * @return Lista de porcentajes por hábito en el mes actual, o 404 si no hay datos
     */
    public ResponseEntity<List<ProgressPercentageDto>> getMonthlyProgressByUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOpt.get();

        List<Habit> habits = habitRepository.findByUser(user);
        if (habits.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        LocalDate now = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());

        List<ProgressPercentageDto> progressPercentageList = new ArrayList<>();

        for (Habit habit : habits) {
            Optional<Goal> goalOpt = goalRepository.findByHabit(habit);
            if (!goalOpt.isPresent()) {
                continue;
            }

            Goal goal = goalOpt.get();
            Frecuency frequency = goal.getFrequency();
            if (frequency == null) {
                continue;
            }

            List<Progress> progressList = progressRepository.findByGoalAndDateBetween(goal, start, end);

            int expectedCount = calculateExpectedCount(frequency, now, start);
            int completedCount = 0;
            for (Progress progress : progressList) {
                if (Boolean.TRUE.equals(progress.getCompleted())) {
                    completedCount++;
                }
            }

            double percentage = expectedCount > 0 ? (completedCount * 100.0) / expectedCount : 0.0;

            ProgressPercentageDto dto = new ProgressPercentageDto(habit.getHabitId(), habit.getName(), percentage);
            progressPercentageList.add(dto);
        }

        return ResponseEntity.ok(progressPercentageList);
    }

    /**
     * Calcula el número esperado de registros de progreso en el mes, basado en la frecuencia.
     *
     * @param frequency Frecuencia de la meta (diaria, semanal, mensual)
     * @param now       Fecha actual
     * @param start     Primer día del mes
     * @return Número esperado de entradas de progreso
     */
    private int calculateExpectedCount(Frecuency frequency, LocalDate now, LocalDate start) {
        switch (frequency) {
            case DAILY:
                return now.lengthOfMonth();
            case WEEKLY:
                int firstDay = start.getDayOfWeek().getValue();
                int totalDays = now.lengthOfMonth();
                int remainingDays = totalDays - (8 - firstDay);
                return 1 + (remainingDays / 7);
            case MONTHLY:
                return 1;
            default:
                return 0;
        }
    }

}
