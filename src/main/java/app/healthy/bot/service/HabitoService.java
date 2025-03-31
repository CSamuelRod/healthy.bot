package app.healthy.bot.service;

import app.healthy.bot.model.Habito;
import app.healthy.bot.reposiroty.HabitoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitoService {
    private final HabitoRepository habitoRepository;

    public HabitoService(HabitoRepository habitoRepository) {
        this.habitoRepository = habitoRepository;
    }

    public List<Habito> obtenerTodos() {
        return habitoRepository.findAll();
    }

    public Optional<Habito> obtenerPorId(Long id) {
        return habitoRepository.findById(id);
    }

    public Habito guardar(Habito habito) {
        return habitoRepository.save(habito);
    }

    public void eliminar(Long id) {
        habitoRepository.deleteById(id);
    }
}
