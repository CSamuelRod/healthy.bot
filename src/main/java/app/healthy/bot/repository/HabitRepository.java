package app.healthy.bot.repository;

import app.healthy.bot.model.Habit;
import app.healthy.bot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByUser(User user); // <- ¡así es correcto!
}
