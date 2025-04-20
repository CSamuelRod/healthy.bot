package app.healthy.bot.service;

import app.healthy.bot.dto.HabitDto;
import app.healthy.bot.model.Habit;
import app.healthy.bot.model.User;
import app.healthy.bot.repository.HabitRepository;
import app.healthy.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Importante para que Spring lo detecte
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    @Autowired
    public HabitService(HabitRepository habitRepository, UserRepository userRepository) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    public HabitDto createHabit(HabitDto dto) {
        Habit habit = new Habit();
        habit.setName(dto.getName());
        habit.setDescription(dto.getDescription());


        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            habit.setUser(user);
        }

        Habit saved = habitRepository.save(habit);

        dto.setHabitId(saved.getHabitId());
        return dto;
    }

    public List<HabitDto> getHabitsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return habitRepository.findByUser(user)
                .stream()
                .map(habit -> {
                    HabitDto dto = new HabitDto();
                    dto.setHabitId(habit.getHabitId());
                    dto.setName(habit.getName());
                    dto.setDescription(habit.getDescription());
                    dto.setUserId(user.getUserId()); // Ya lo tienes
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
