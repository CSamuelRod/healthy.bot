package app.healthy.bot.dto;

import java.time.LocalDate;

public class ProgressDto {
    private Long goalId;
    private LocalDate date;
    private Boolean completed;
    private String notes;


    public ProgressDto() {
    }

    public ProgressDto(Long goalId, LocalDate date, Boolean completed, String notes) {
        this.goalId = goalId;
        this.date = date;
        this.completed = completed;
        this.notes = notes;
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
