package app.healthy.bot.service;

import app.healthy.bot.model.Meta;
import app.healthy.bot.reposiroty.MetaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MetaService {

    private final MetaRepository metaRepository;

    public MetaService(MetaRepository metaRepository) {
        this.metaRepository = metaRepository;
    }

    public List<Meta> obtenerPorHabito(Long habitoId) {
        return new ArrayList<Meta>();
        //return metaRepository.findByHabitoId(habitoId);
    }

    public Optional<Meta> obtenerPorId(Long id) {
        return metaRepository.findById(id);
    }

    public Meta guardar(Meta meta) {
        return metaRepository.save(meta);
    }

    public void eliminar(Long id) {
        metaRepository.deleteById(id);
    }
}