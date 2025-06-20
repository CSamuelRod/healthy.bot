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

/**
 * Servicio para gestionar los hábitos de los usuarios.
 * <p>
 * Incluye operaciones para crear, actualizar, consultar y eliminar hábitos.
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
     * Crea un nuevo hábito asociado a un usuario existente.
     *
     * @param dto Objeto DTO con los datos del hábito a crear
     * @return Objeto DTO con el hábito creado, incluyendo su ID
     * @throws RuntimeException si el usuario no existe
     */
    public HabitDto createHabit(HabitDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Habit habit = dto.toEntity(user);
        Habit saved = habitRepository.save(habit);

        return HabitDto.fromEntity(saved);
    }

    /**
     * Actualiza un hábito ya existente.
     *
     * @param habitId ID del hábito a actualizar
     * @param dto     DTO con los nuevos datos del hábito
     * @return DTO actualizado
     * @throws RuntimeException si el hábito no existe
     */
    public HabitDto updateHabit(Long habitId, HabitDto dto) {
        Habit existingHabit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Hábito no encontrado"));

        existingHabit.setName(dto.getName());
        existingHabit.setDescription(dto.getDescription());

        Habit updated = habitRepository.save(existingHabit);
        return HabitDto.fromEntity(updated);
    }

    /**
     * Obtiene un hábito por su ID.
     *
     * @param habitId ID del hábito
     * @return DTO del hábito encontrado
     * @throws RuntimeException si el hábito no existe
     */
    public HabitDto getHabitById(Long habitId) {
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new RuntimeException("Hábito no encontrado"));

        return HabitDto.fromEntity(habit);
    }

    /**
     * Obtiene todos los hábitos de un usuario específico.
     *
     * @param userId ID del usuario
     * @return Lista de DTOs representando los hábitos del usuario
     * @throws RuntimeException si el usuario no existe
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
     * Elimina un hábito si existe.
     *
     * @param habitId ID del hábito a eliminar
     */
    public void deleteHabit(Long habitId) {
        Optional<Habit> habit = habitRepository.findById(habitId);
        habit.ifPresent(h -> habitRepository.deleteById(habitId));
    }
}
