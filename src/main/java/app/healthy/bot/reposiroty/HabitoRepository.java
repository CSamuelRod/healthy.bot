package app.healthy.bot.reposiroty;

import app.healthy.bot.model.Habito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitoRepository extends JpaRepository<Habito,Long> {

}
