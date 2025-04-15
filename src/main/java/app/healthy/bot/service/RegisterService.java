package app.healthy.bot.service;


import app.healthy.bot.model.User;
import app.healthy.bot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String register(String nombre,
                           String apellido,
                           String email,
                           String rawPassword) {
        // Verificamos si el correo ya está registrado
        if (userRepository.findByEmail(email).isPresent()) {
            return "El correo ya está registrado";
        }

        // Creamos el usuario y encriptamos su contraseña
        User user = new User();
        user.setName(nombre);
        user.setLastName(apellido);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRegistrationDate(LocalDate.now());  // Fecha de registro automática

        // Guardamos el usuario en la base de datos
        userRepository.save(user);

        // Mensaje de éxito
        return "Usuario registrado con éxito";
    }
}
