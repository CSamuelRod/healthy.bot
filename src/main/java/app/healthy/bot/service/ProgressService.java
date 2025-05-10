package app.healthy.bot.service;

import app.healthy.bot.dto.ProgressDto;
import app.healthy.bot.model.Goal;
import app.healthy.bot.model.Progress;
import app.healthy.bot.repository.GoalRepository;
import app.healthy.bot.repository.ProgressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;
    private final GoalRepository goalRepository;

    public ProgressService(ProgressRepository progressRepository, GoalRepository goalRepository) {
        this.progressRepository = progressRepository;
        this.goalRepository = goalRepository;
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

    public Optional<Progress> findById(Long id) {
        return progressRepository.findById(id);
    }

    public void delete(Long id) {
        progressRepository.deleteById(id);
    }
}