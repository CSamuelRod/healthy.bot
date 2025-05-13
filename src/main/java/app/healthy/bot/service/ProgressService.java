package app.healthy.bot.service;

import app.healthy.bot.dto.ProgressDto;
import app.healthy.bot.model.Goal;
import app.healthy.bot.model.Habit;
import app.healthy.bot.model.Progress;
import app.healthy.bot.repository.GoalRepository;
import app.healthy.bot.repository.HabitRepository;
import app.healthy.bot.repository.ProgressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final GoalRepository goalRepository;
    private final HabitRepository habitRepository;

    public ProgressService(ProgressRepository progressRepository, GoalRepository goalRepository, HabitRepository habitRepository) {
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

    public ResponseEntity<List<ProgressDto>> getProgressListByHabitId(Long id) {
        Optional<Habit> habitOpt = habitRepository.findById(id);
        if (habitOpt.isEmpty()) return ResponseEntity.notFound().build();

        Optional<Goal> goalOpt = goalRepository.findByHabit(habitOpt.get());
        if (goalOpt.isEmpty()) return ResponseEntity.notFound().build();

        Goal goal = goalOpt.get();

        LocalDate now = LocalDate.now();
        LocalDate start = now.withDayOfMonth(1);
        LocalDate end = now.withDayOfMonth(now.lengthOfMonth());

        List<Progress> progressList = progressRepository.findByGoalAndDateBetween(goal, start, end);

        List<ProgressDto> dtos = progressList.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
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