package app.healthy.bot.repository;

import app.healthy.bot.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
}
