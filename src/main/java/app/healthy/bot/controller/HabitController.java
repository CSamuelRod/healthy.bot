package app.healthy.bot.controller;

import app.healthy.bot.dto.HabitDto;
import app.healthy.bot.service.HabitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar hábitos de los usuarios.
 * Proporciona endpoints para crear, obtener y eliminar hábitos.
 */
@RestController
@RequestMapping("/api/habits")
@CrossOrigin
public class HabitController {

    private final HabitService habitService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param habitService Servicio que gestiona la lógica de los hábitos
     */
    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    /**
     * Crea un nuevo hábito para un usuario específico.
     *
     * @param userId   ID del usuario al que se le asignará el hábito
     * @param habitDto DTO con los datos del hábito a crear
     * @return ResponseEntity con el hábito creado y código HTTP 200
     */
    @PostMapping("/{userId}")
    public ResponseEntity<HabitDto> createHabit(
            @PathVariable Long userId,
            @RequestBody HabitDto habitDto
    ) {
        habitDto.setUserId(userId);
        HabitDto createdHabit = habitService.createHabit(habitDto);
        return ResponseEntity.ok(createdHabit);
    }

    /**
     * Obtiene todos los hábitos asociados a un usuario específico.
     *
     * @param userId ID del usuario
     * @return ResponseEntity con la lista de hábitos y código HTTP 200
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<HabitDto>> getHabitsByUserId(@PathVariable Long userId) {
        List<HabitDto> habits = habitService.getHabitsByUserId(userId);
        return ResponseEntity.ok(habits);
    }

    /**
     * Elimina un hábito dado su ID.
     *
     * @param habitId ID del hábito a eliminar
     * @return ResponseEntity con código HTTP 204 si se elimina correctamente
     */
    @DeleteMapping("/{habitId}")
    public ResponseEntity<Void> delete(@PathVariable Long habitId) {
        habitService.deleteHabit(habitId);
        return ResponseEntity.noContent().build();
    }
}
