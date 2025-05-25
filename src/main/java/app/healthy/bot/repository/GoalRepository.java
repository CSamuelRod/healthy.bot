package app.healthy.bot.repository;

import app.healthy.bot.model.Goal;
import app.healthy.bot.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal,Long> {

    Optional<Goal> findByHabit(Habit habit);

}
