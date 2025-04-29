package app.healthy.bot.controller;

import app.healthy.bot.dto.GoalDto;
import app.healthy.bot.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    // Obtener Goal por HabitId
    @GetMapping("/{habitId}")
    public ResponseEntity<GoalDto> getGoalByHabitId(@PathVariable Long habitId) {
        try {
            GoalDto goalDto = goalService.getGoalByHabitId(habitId);
            return new ResponseEntity<>(goalDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Si no se encuentra el Goal, retornar un error 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Crear Goal
    @PostMapping("/")
    public ResponseEntity<GoalDto> createGoal(@RequestBody GoalDto goalDto) {
        try {
            GoalDto createdGoal = goalService.createGoal(goalDto);
            return new ResponseEntity<>(createdGoal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    // Actualizar Goal
    @PutMapping("/{goalId}")
    public ResponseEntity<GoalDto> updateGoal(@PathVariable Long goalId, @RequestBody GoalDto goalDto) {
        try {
            GoalDto updatedGoal = goalService.updateGoal(goalId, goalDto);
            return new ResponseEntity<>(updatedGoal, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Eliminar Goal
    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long goalId) {
        try {
            goalService.deleteGoal(goalId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
