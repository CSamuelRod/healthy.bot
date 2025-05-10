package app.healthy.bot.controller;

import app.healthy.bot.dto.HabitDto;
import app.healthy.bot.dto.ProgressDto;
import app.healthy.bot.model.Progress;
import app.healthy.bot.service.GoalService;
import app.healthy.bot.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService progressService;
    private final GoalService  goalService;
    public ProgressController(ProgressService progressService, GoalService goalService) {
        this.progressService = progressService;
        this.goalService = goalService;
    }
    @PostMapping
    public ResponseEntity<ProgressDto> saveProgress(@RequestBody ProgressDto progressDto) {
        ProgressDto createdProgress = progressService.save(progressDto);
        return ResponseEntity.ok(createdProgress);
    }



/*
    @GetMapping("/{id}")
    public ResponseEntity<Progress> getProgress(@PathVariable Long id) {
        return progressService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/goal/{goalId}")
    public ResponseEntity<List<Progress>> getByGoal(@PathVariable Long goalId) {
        return ResponseEntity.ok(progressService.findByGoalId(goalId));
    }

    @GetMapping("/habit/{habitId}")
    public ResponseEntity<List<Progress>> getByHabit(@PathVariable Long habitId) {
        return ResponseEntity.ok(progressService.findByHabitId(habitId));
    }

    @GetMapping("/habit/{habitId}/date/{date}")
    public ResponseEntity<List<Progress>> getByHabitAndDate(@PathVariable Long habitId, @PathVariable String date) {
        return ResponseEntity.ok(progressService.findByHabitIdAndDate(habitId, LocalDate.parse(date)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        progressService.delete(id);
        return ResponseEntity.noContent().build();
    }

*/
}
