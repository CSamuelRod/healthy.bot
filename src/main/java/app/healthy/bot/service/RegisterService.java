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

        // Validaciones básicas del lado del backend

        if (nombre == null || nombre.trim().isEmpty() ||
                apellido == null || apellido.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                rawPassword == null || rawPassword.trim().isEmpty()) {
            return "Todos los campos son obligatorios";
        }

        if (!email.matches("^[A-Za-z0-9._%+-]+@(gmail\\.com|hotmail\\.com)$")) {
            return "El correo debe ser válido y terminar en @gmail.com o @hotmail.com";
        }

        if (rawPassword.length() < 6 ||
                !rawPassword.matches(".*[A-Za-z].*") || // debe contener letras
                !rawPassword.matches(".*[0-9].*")) {    // debe contener números
            return "La contraseña debe tener al menos 6 caracteres, incluir letras y números";
        }

        if (userRepository.findByEmail(email).isPresent()) {
            return "El correo ya está registrado";
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = fromRaw(nombre, apellido, email, encodedPassword);
        userRepository.save(user);

        return "Usuario registrado con éxito";
    }

}
