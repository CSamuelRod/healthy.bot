package app.healthy.bot.dto;

public class ProgressPercentageDto {
    private Long habitId;
    private String habitName;
    private double progressPercentage;

    public ProgressPercentageDto(Long habitId, String habitName, double progressPercentage) {
        this.habitId = habitId;
        this.habitName = habitName;
        this.progressPercentage = progressPercentage;

    }

    public Long getHabitId() {
        return habitId;
    }

    public String getHabitName() {
        return habitName;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }
}