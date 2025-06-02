package app.healthy.bot.service;

import app.healthy.bot.dto.LoginRequest;
import app.healthy.bot.dto.UserDto;
import app.healthy.bot.model.User;
import app.healthy.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Servicio encargado de manejar la lógica de negocio relacionada con usuarios
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param userRepository Repositorio de usuarios
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Busca y retorna un usuario en formato DTO a partir de su ID.
     *
     * @param id ID único del usuario
     * @return UserDto con los datos del usuario
     * @throws RuntimeException si no se encuentra el usuario
     */
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return UserDto.fromEntity(user);
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param id ID del usuario a actualizar
     * @param userDto Objeto DTO con los nuevos datos
     * @return UserDto con los datos actualizados
     * @throws NoSuchElementException si el usuario no existe
     * @throws IllegalStateException si el correo ya está en uso por otro usuario
     */
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        Optional<User> existing = userRepository.findByEmail(userDto.getEmail());
        if (existing.isPresent() && !existing.get().getUserId().equals(id)) {
            throw new IllegalStateException("Correo ya existente");
        }


        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        User savedUser = userRepository.save(user);

        return UserDto.fromEntity(savedUser);
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
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

    /**
     * Reinicia la contraseña de un usuario usando su correo electrónico.
     *
     * @param request Objeto con el email y la nueva contraseña
     * @return LoginRequest con el email si se realizó correctamente,
     *         o con un mensaje de error en el campo email si falló
     */
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