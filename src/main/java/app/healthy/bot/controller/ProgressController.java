package app.healthy.bot.controller;

import app.healthy.bot.dto.ProgressDto;
import app.healthy.bot.dto.ProgressPercentageDto;
import app.healthy.bot.service.GoalService;
import app.healthy.bot.service.ProgressService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para gestionar el progreso de los hábitos.
 * Permite guardar, eliminar y consultar registros de progreso, así como obtener el porcentaje de progreso mensual.
 */
@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService progressService;
    private final GoalService goalService;

    public ProgressController(ProgressService progressService, GoalService goalService) {
        this.progressService = progressService;
        this.goalService = goalService;
    }

    /**
     * Guarda un registro de progreso.
     *
     * @param progressDto DTO con los datos del progreso a guardar
     * @return ResponseEntity con el progreso guardado y código HTTP 200
     */
    @PostMapping
    public ResponseEntity<ProgressDto> saveProgress(@RequestBody ProgressDto progressDto) {
        ProgressDto createdProgress = progressService.save(progressDto);
        return ResponseEntity.ok(createdProgress);
    }

    /**
     * Elimina un registro de progreso dado su ID.
     *
     * @param id ID del progreso a eliminar
     * @return ResponseEntity con código HTTP 204 si se elimina correctamente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        progressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene los registros de progreso únicos de un usuario para una fecha específica.
     *
     * @param userId ID del usuario
     * @param date Fecha del progreso en formato ISO (yyyy-MM-dd)
     * @return ResponseEntity con la lista de progresos encontrados y código HTTP 200
     */
    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<List<ProgressDto>> getProgressByUserAndDate(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<ProgressDto> progressList = progressService.getUniqueProgressByUserAndDate(userId, date);
        return ResponseEntity.ok(progressList);
    }

    /**
     * Obtiene el porcentaje de progreso mensual por usuario.
     *
     * @param userId ID del usuario
     * @return ResponseEntity con la lista de porcentajes de progreso y código HTTP 200 o 404 si no existe usuario o hábitos
     */
    @GetMapping("/habit/{userId}/progress-percentage")
    public ResponseEntity<List<ProgressPercentageDto>> getProgressPercentageByUserId(@PathVariable Long userId) {
        return progressService.getMonthlyProgressByUser(userId);
    }
}
