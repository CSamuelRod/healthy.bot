package app.healthy.bot.dto;

import app.healthy.bot.model.Progress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressDto {
    private Long progressId;
    private Long goalId;
    private LocalDate date;
    private Boolean completed;
    private String notes;

    /**
     * Convierte una entidad Progress a su representación DTO.
     *
     * @param progress Entidad Progress
     * @return DTO correspondiente
     */
    public static ProgressDto fromEntity(Progress progress) {
        return new ProgressDto(
                progress.getProgressId(),
                progress.getGoal().getGoal_Id(),
                progress.getDate(),
                progress.getCompleted(),
                progress.getNotes()
        );
    }
}
