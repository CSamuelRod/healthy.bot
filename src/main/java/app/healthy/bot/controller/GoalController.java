package app.healthy.bot.controller;

import app.healthy.bot.dto.GoalDto;
import app.healthy.bot.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar metas (goals) asociadas a hábitos.
 * Proporciona endpoints para crear, obtener, actualizar y eliminar metas.
 */
@RestController
@RequestMapping("/api/goals")
@CrossOrigin
public class GoalController {

    private final GoalService goalService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param goalService Servicio que gestiona la lógica de las metas
     */
    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    /**
     * Obtiene una meta (goal) asociada a un hábito dado.
     *
     * @param habitId ID del hábito
     * @return ResponseEntity con el DTO de la meta y código HTTP 200 si existe, 404 si no se encuentra
     */
    @GetMapping("/{habitId}")
    public ResponseEntity<GoalDto> getGoalByHabitId(@PathVariable Long habitId) {
        try {
            GoalDto goalDto = goalService.getGoalByHabitId(habitId);
            return ResponseEntity.ok(goalDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Crea una nueva meta (goal) para un hábito.
     *
     * @param goalDto DTO con los datos de la meta a crear
     * @return ResponseEntity con el DTO creado y código HTTP 201 si se guarda correctamente,
     *         400 si ocurre algún error de validación o procesamiento
     */
    @PostMapping("/")
    public ResponseEntity<GoalDto> createGoal(@RequestBody GoalDto goalDto) {
        try {
            GoalDto createdGoal = goalService.createGoal(goalDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGoal);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Actualiza una meta existente.
     *
     * @param goalId  ID de la meta a actualizar
     * @param goalDto DTO con los nuevos datos
     * @return ResponseEntity con el DTO actualizado y código HTTP 200 si se actualiza correctamente,
     *         400 si ocurre algún error
     */
    @PutMapping("/{goalId}")
    public ResponseEntity<GoalDto> updateGoal(@PathVariable Long goalId, @RequestBody GoalDto goalDto) {
        try {
            GoalDto updatedGoal = goalService.updateGoal(goalId, goalDto);
            return ResponseEntity.ok(updatedGoal);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Elimina una meta existente por su ID.
     *
     * @param goalId ID de la meta a eliminar
     * @return ResponseEntity con código HTTP 204 si se elimina correctamente,
     *         404 si no se encuentra la meta
     */
    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long goalId) {
        try {
            goalService.deleteGoal(goalId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
