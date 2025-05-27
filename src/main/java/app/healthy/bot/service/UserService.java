package app.healthy.bot.service;

import app.healthy.bot.dto.LoginRequest;
import app.healthy.bot.dto.UserDto;
import app.healthy.bot.model.User;
import app.healthy.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio para operaciones relacionadas con usuarios.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    public LoginRequest resetPassword(LoginRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            // Usuario no encontrado, devolvemos DTO con mensaje de error en email
            return new LoginRequest("email no encontrado", null);
        }
        // Actualizamos la contraseña
        User userFinal = user.get();
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        userFinal.setPassword(encodedPassword);
        userRepository.save(userFinal);
        // Devolvemos DTO con email original y password null para indicar éxito
        return new LoginRequest(userFinal.getEmail(), null);
    }


}
