package app.healthy.bot.repository;

import app.healthy.bot.model.Goal;
import app.healthy.bot.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    @Query("""
    SELECT p FROM Progress p
    JOIN p.goal g
    JOIN g.habit h
    JOIN h.user u
    WHERE u.id = :userId AND p.date = :date
""")
    List<Progress> findUniqueProgress(@Param("userId") Long userId, @Param("date") LocalDate date);


    List<Progress> findByGoalAndDateBetween(Goal goal, LocalDate start, LocalDate end);

}
