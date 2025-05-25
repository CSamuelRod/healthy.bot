package app.healthy.bot.service;

import app.healthy.bot.dto.UserDto;
import app.healthy.bot.model.User;
import app.healthy.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static app.healthy.bot.dto.UserDto.fromRaw;

/**
 * Servicio para el registro de nuevos usuarios.
 */
@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Registra un nuevo usuario si el correo no está ya registrado.
     *
     * @param nombre      Nombre del usuario
     * @param apellido    Apellido del usuario
     * @param email       Correo electrónico del usuario
     * @param rawPassword Contraseña en texto plano
     * @return Mensaje indicando éxito o error
     */
    public String register(String nombre,
                           String apellido,
                           String email,
                           String rawPassword) {
        if (userRepository.findByEmail(email).isPresent()) {
            return "El correo ya está registrado";
        }

        // Codificar la contraseña
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Usar método auxiliar desde el DTO
        User user = fromRaw(nombre, apellido, email, encodedPassword);

        // Guardar en la base de datos
        userRepository.save(user);

        return "Usuario registrado con éxito";
    }
}
