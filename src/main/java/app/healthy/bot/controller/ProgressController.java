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



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        progressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/habit/{id}")
    public ResponseEntity<List<ProgressDto>> getProgressListByHabitId(@PathVariable Long id) {
        return progressService.getProgressListByHabitId(id);
    }


}
