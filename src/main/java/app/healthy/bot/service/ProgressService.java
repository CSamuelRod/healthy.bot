package app.healthy.bot.service;

import app.healthy.bot.model.Progress;
import app.healthy.bot.repository.ProgressRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;

    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public Progress save(Progress progress) {
        return progressRepository.save(progress);
    }

    public Optional<Progress> findById(Long id) {
        return progressRepository.findById(id);
    }
    public void delete(Long id) {
        progressRepository.deleteById(id);
    }


}
