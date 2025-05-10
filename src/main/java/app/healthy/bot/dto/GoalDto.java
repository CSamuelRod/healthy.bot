package app.healthy.bot.dto;

import app.healthy.bot.enums.Frecuency;


import java.time.LocalDate;


public class GoalDto {
    private Long goalId; // 👈 NUEVO: ID del Goal
    private Long user_id;
    private Long habit_id;
    private String objective;
    private Frecuency frequency;
    private LocalDate startDate;
    private LocalDate endDate;

    public GoalDto(Long goalId, Long user_id, Long habit_id, String objective, Frecuency frequency, LocalDate startDate, LocalDate endDate) {
        this.goalId = goalId;
        this.user_id = user_id;
        this.habit_id = habit_id;
        this.objective = objective;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public GoalDto() {
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getHabit_id() {
        return habit_id;
    }

    public void setHabit_id(Long habit_id) {
        this.habit_id = habit_id;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public Frecuency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frecuency frequency) {
        this.frequency = frequency;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
