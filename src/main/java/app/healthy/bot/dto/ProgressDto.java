package app.healthy.bot.dto;

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


}