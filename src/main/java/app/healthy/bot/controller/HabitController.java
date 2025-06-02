package app.healthy.bot.controller;

import app.healthy.bot.dto.HabitDto;
import app.healthy.bot.service.HabitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar hábitos de los usuarios.
 * <p>
 * Proporciona endpoints para crear, actualizar, obtener y eliminar hábitos.
 */
@RestController
@RequestMapping("/api/habits")
@CrossOrigin
public class HabitController {

    private final HabitService habitService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param habitService Servicio que contiene la lógica de negocio para hábitos
     */
    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    /**
     * Crea un nuevo hábito para un usuario.
     *
     * @param userId   ID del usuario al que se le asignará el hábito
     * @param habitDto DTO con los datos del hábito
     * @return ResponseEntity con el hábito creado
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
     * Actualiza los datos de un hábito existente.
     *
     * @param habitId  ID del hábito a actualizar
     * @param habitDto DTO con los nuevos datos
     * @return ResponseEntity con el hábito actualizado
     */
    @PutMapping("/{habitId}")
    public ResponseEntity<HabitDto> updateHabit(
            @PathVariable Long habitId,
            @RequestBody HabitDto habitDto
    ) {
        HabitDto updatedHabit = habitService.updateHabit(habitId, habitDto);
        return ResponseEntity.ok(updatedHabit);
    }

    /**
     * Obtiene un hábito por su ID.
     *
     * @param habitId ID del hábito
     * @return ResponseEntity con el hábito encontrado o 404 si no existe
     */
    @GetMapping("/{habitId}")
    public ResponseEntity<HabitDto> getHabitById(@PathVariable Long habitId) {
        HabitDto habitDto = habitService.getHabitById(habitId);
        if (habitDto != null) {
            return ResponseEntity.ok(habitDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene todos los hábitos de un usuario.
     *
     * @param userId ID del usuario
     * @return ResponseEntity con lista de hábitos
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HabitDto>> getHabitsByUserId(@PathVariable Long userId) {
        List<HabitDto> habits = habitService.getHabitsByUserId(userId);
        return ResponseEntity.ok(habits);
    }

    /**
     * Elimina un hábito por su ID.
     *
     * @param habitId ID del hábito
     * @return ResponseEntity con código 204 No Content
     */
    @DeleteMapping("/{habitId}")
    public ResponseEntity<Void> delete(@PathVariable Long habitId) {
        habitService.deleteHabit(habitId);
        return ResponseEntity.noContent().build();
    }
}
