package app.healthy.bot.service;

import app.healthy.bot.dto.UserDto;
import app.healthy.bot.model.User;
import app.healthy.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static app.healthy.bot.dto.UserDto.fromRaw;

/**
 * Servicio encargado del registro de nuevos usuarios.
 * <p>
 * Valida los datos ingresados (nombre, apellido, correo y contraseña),
 * verifica que el correo no esté registrado previamente y guarda el nuevo usuario
 * con la contraseña cifrada.
 */
@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Registra un nuevo usuario en el sistema.
     * <p>
     * Realiza validaciones básicas sobre los datos de entrada,
     * incluyendo formato de correo, fuerza de contraseña y unicidad del correo.
     *
     * @param nombre      Nombre del usuario
     * @param apellido    Apellido del usuario
     * @param email       Correo electrónico del usuario
     * @param rawPassword Contraseña en texto plano
     * @return Cadena con mensaje indicando el resultado del registro:
     *         - Éxito si el usuario fue registrado
     *         - Descripción del error si alguna validación falla
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
                !rawPassword.matches(".*[A-Za-z].*") ||  // debe contener letras
                !rawPassword.matches(".*[0-9].*")) {     // debe contener números
            return "La contraseña debe tener al menos 6 caracteres, incluir letras y números";
        }

        if (userRepository.findByEmail(email).isPresent()) {
            return "El correo ya está registrado";
        }

        // Registro del usuario
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = fromRaw(nombre, apellido, email, encodedPassword);
        userRepository.save(user);

        return "Usuario registrado con éxito";
    }

}
