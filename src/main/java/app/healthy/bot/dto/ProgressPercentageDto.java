package app.healthy.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgressPercentageDto {
    private Long habitId;
    private String habitName;
    private double progressPercentage;

}