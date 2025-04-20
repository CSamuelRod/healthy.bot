package app.healthy.bot.controller;

import app.healthy.bot.dto.HabitDto;
import app.healthy.bot.service.HabitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
@CrossOrigin
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    // Crear hábito para un usuario
    @PostMapping("/{userId}")
    public ResponseEntity<HabitDto> createHabit(
            @PathVariable Long userId,
            @RequestBody HabitDto habitDto
    ) {
        habitDto.setUserId(userId);
        HabitDto createdHabit = habitService.createHabit(habitDto);
        return ResponseEntity.ok(createdHabit);
    }

    // Obtener hábitos de un usuario
    @GetMapping("/{userId}")
    public ResponseEntity<List<HabitDto>> getHabitsByUserId(@PathVariable Long userId) {
        List<HabitDto> habits = habitService.getHabitsByUserId(userId);
        return ResponseEntity.ok(habits);
    }
}
