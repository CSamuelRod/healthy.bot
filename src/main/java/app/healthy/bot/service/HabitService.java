package app.healthy.bot.service;

import app.healthy.bot.dto.HabitDto;
import app.healthy.bot.model.Habit;
import app.healthy.bot.model.User;
import app.healthy.bot.repository.HabitRepository;
import app.healthy.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar hábitos de los usuarios.
 */
@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    @Autowired
    public HabitService(HabitRepository habitRepository, UserRepository userRepository) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    /**
     * Crea un nuevo hábito asociado a un usuario.
     *
     * @param dto DTO con los datos del hábito
     * @return DTO con el hábito creado y su ID asignado
     */
    public HabitDto createHabit(HabitDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Habit habit = dto.toEntity(user);
        Habit saved = habitRepository.save(habit);

        return HabitDto.fromEntity(saved);
    }

    /**
     * Obtiene la lista de hábitos asociados a un usuario.
     *
     * @param userId ID del usuario
     * @return Lista de DTOs con los hábitos del usuario
     */
    public List<HabitDto> getHabitsByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = optionalUser.get();
        List<Habit> habits = habitRepository.findByUser(user);

        List<HabitDto> dtoList = new ArrayList<>();
        for (Habit habit : habits) {
            HabitDto dto = HabitDto.fromEntity(habit);
            dtoList.add(dto);
        }

        return dtoList;
    }

    /**
     * Elimina un hábito dado su ID.
     *
     * @param habitId ID del hábito a eliminar
     */
    public void deleteHabit(Long habitId) {
        Optional<Habit> habit = habitRepository.findById(habitId);
        habit.ifPresent(h -> habitRepository.deleteById(habitId));
    }
}
