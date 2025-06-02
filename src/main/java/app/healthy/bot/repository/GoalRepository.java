package app.healthy.bot.repository;

import app.healthy.bot.model.Goal;
import app.healthy.bot.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal,Long> {

    Optional<Goal> findByHabit(Habit habit);

}
