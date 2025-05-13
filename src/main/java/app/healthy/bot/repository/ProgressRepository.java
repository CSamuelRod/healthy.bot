package app.healthy.bot.repository;

import app.healthy.bot.model.Goal;
import app.healthy.bot.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    List<Progress> findByGoalAndDateBetween(Goal goal, LocalDate start, LocalDate end);

}
