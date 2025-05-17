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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static app.healthy.bot.enums.Frecuency.*;

@Service
public class ProgressService {
    private final UserRepository userRepository;
    private final ProgressRepository progressRepository;
    private final GoalRepository goalRepository;
    private final HabitRepository habitRepository;

    public ProgressService(UserRepository userRepository, ProgressRepository progressRepository, GoalRepository goalRepository, HabitRepository habitRepository) {
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
        this.goalRepository = goalRepository;
        this.habitRepository = habitRepository;
    }

    public ProgressDto save(ProgressDto progressDto) {
        // Buscar el Goal por ID
        Goal goal = goalRepository.findById(progressDto.getGoalId())
                .orElseThrow(() -> new RuntimeException("Goal no encontrado con ID: " + progressDto.getGoalId()));

        // Crear una nueva entidad Progress
        Progress progress = new Progress();
        progress.setGoal(goal);
        progress.setDate(progressDto.getDate());
        progress.setCompleted(progressDto.getCompleted());
        progress.setNotes(progressDto.getNotes());

        // Guardar
        Progress saved = progressRepository.save(progress);

        // Devolver el DTO directamente sin método aparte
        return new ProgressDto(
                saved.getGoal().getGoal_Id(),
                saved.getDate(),
                saved.getCompleted(),
                saved.getNotes()
        );
    }


    public void delete(Long id) {
        progressRepository.deleteById(id);
    }

    public ResponseEntity<List<ProgressPercentageDto>> getMonthlyProgressByUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
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
            if (goalOpt.isEmpty()) {
                // Si no hay goal asociado, simplemente continuamos con el siguiente hábito
                continue;
            }
            Goal goal = goalOpt.get();

            Frecuency frequency = goal.getFrequency();
            if (frequency == null) {
                continue; // Si no hay frecuencia, ignoramos este hábito
            }

            List<Progress> progressList = progressRepository.findByGoalAndDateBetween(goal, start, end);

            int expectedCount = 0;
            switch (frequency) {
                case DAILY:
                    expectedCount = now.lengthOfMonth();
                    break;
                case WEEKLY:
                    int firstDay = start.getDayOfWeek().getValue(); // 1=Lunes ... 7=Domingo
                    int totalDays = now.lengthOfMonth();
                    int remainingDays = totalDays - (8 - firstDay);
                    expectedCount = 1 + (remainingDays / 7);
                    break;
                case MONTHLY:
                    expectedCount = 1;
                    break;
            }

            long completedCount = 0;
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



    private ProgressDto toDto(Progress progress) {
        return new ProgressDto(
                progress.getGoal().getGoal_Id(),
                progress.getDate(),
                progress.getCompleted(),
                progress.getNotes()
        );
    }

}