package app.healthy.bot.service;

import app.healthy.bot.model.User;
import app.healthy.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio encargado de manejar la autenticación de usuarios mediante validación de credenciales.
 */
@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Valida las credenciales de un usuario.
     * <p>
     * Compara el correo electrónico y la contraseña proporcionada (en texto plano)
     * con las credenciales almacenadas en la base de datos. Si coinciden, retorna el usuario.
     *
     * @param email       Correo electrónico del usuario
     * @param rawPassword Contraseña en texto plano
     * @return Optional con el usuario autenticado si las credenciales son válidas,
     *         o vacío si son incorrectas
     */
    public Optional<User> login(String email, String rawPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }
}
