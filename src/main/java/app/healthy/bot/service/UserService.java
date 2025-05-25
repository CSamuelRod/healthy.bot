package app.healthy.bot.service;

import app.healthy.bot.dto.UserDto;
import app.healthy.bot.model.User;
import app.healthy.bot.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Servicio para operaciones relacionadas con usuarios.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id ID del usuario
     * @return DTO con los datos del usuario
     * @throws RuntimeException si el usuario no existe
     */
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return UserDto.fromEntity(user);
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param id      ID del usuario a actualizar
     * @param userDto DTO con los nuevos datos
     * @return DTO actualizado
     * @throws RuntimeException si el usuario no existe
     */
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        userRepository.save(user);
        return UserDto.fromEntity(user);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id ID del usuario a eliminar
     * @throws RuntimeException si el usuario no existe
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }
}
