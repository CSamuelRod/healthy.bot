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
import java.util.stream.Collectors;

@Service
public class ProgressService {

    private final UserRepository userRepository;
    private final ProgressRepository progressRepository;
    private final GoalRepository goalRepository;
    private final HabitRepository habitRepository;

    public ProgressService(UserRepository userRepository,
                           ProgressRepository progressRepository,
                           GoalRepository goalRepository,
                           HabitRepository habitRepository) {
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
        this.goalRepository = goalRepository;
        this.habitRepository = habitRepository;
    }

    public ProgressDto save(ProgressDto progressDto) {
        Goal goal = goalRepository.findById(progressDto.getGoalId())
                .orElseThrow(() -> new RuntimeException("Goal no encontrado con ID: " + progressDto.getGoalId()));

        Progress progress = new Progress();
        progress.setGoal(goal);
        progress.setDate(progressDto.getDate());
        progress.setCompleted(progressDto.getCompleted());
        progress.setNotes(progressDto.getNotes());

        Progress saved = progressRepository.save(progress);

        return toDto(saved);
    }

    public List<ProgressDto> getUniqueProgressByUserAndDate(Long userId, LocalDate date) {
        List<Progress> progresses = progressRepository.findUniqueProgress(userId, date);
        return progresses.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
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
            if (goalOpt.isEmpty()) continue;

            Goal goal = goalOpt.get();
            Frecuency frequency = goal.getFrequency();
            if (frequency == null) continue;

            List<Progress> progressList = progressRepository.findByGoalAndDateBetween(goal, start, end);

            int expectedCount = calculateExpectedCount(frequency, now, start);
            long completedCount = progressList.stream()
                    .filter(p -> Boolean.TRUE.equals(p.getCompleted()))
                    .count();

            double percentage = expectedCount > 0 ? (completedCount * 100.0) / expectedCount : 0.0;

            progressPercentageList.add(new ProgressPercentageDto(habit.getHabitId(), habit.getName(), percentage));
        }

        return ResponseEntity.ok(progressPercentageList);
    }

    private int calculateExpectedCount(Frecuency frequency, LocalDate now, LocalDate start) {
        switch (frequency) {
            case DAILY:
                return now.lengthOfMonth();
            case WEEKLY:
                int firstDay = start.getDayOfWeek().getValue(); // 1=Monday ... 7=Sunday
                int totalDays = now.lengthOfMonth();
                int remainingDays = totalDays - (8 - firstDay);
                return 1 + (remainingDays / 7);
            case MONTHLY:
                return 1;
            default:
                return 0;
        }
    }

    private ProgressDto toDto(Progress progress) {
        return new ProgressDto(
                progress.getProgressId(),
                progress.getGoal().getGoal_Id(),
                progress.getDate(),
                progress.getCompleted(),
                progress.getNotes()
        );
    }
}
