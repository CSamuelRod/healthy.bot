package app.healthy.bot.controller;

import app.healthy.bot.dto.LoginRequest;
import app.healthy.bot.dto.LoginResponse;
import app.healthy.bot.dto.RegisterRequest;
import app.healthy.bot.dto.RegisterResponse;
import app.healthy.bot.model.User;
import app.healthy.bot.service.LoginService;
import app.healthy.bot.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador REST para gestionar las operaciones de autenticación y registro de usuarios.
 * <p>
 * Provee endpoints para login y registro, comunicándose con los servicios correspondientes.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RegisterService registerService;

    /**
     * Maneja la petición de login de usuarios.
     * <p>
     * Recibe un objeto {@link LoginRequest} con email y contraseña,
     * valida las credenciales usando {@link LoginService} y responde con un {@link LoginResponse}
     * que incluye mensaje y ID de usuario si la autenticación es exitosa.
     * En caso de credenciales inválidas, devuelve código 401 con mensaje correspondiente.
     *
     * @param request Objeto con email y contraseña para autenticación
     * @return Respuesta HTTP con mensaje y usuario autenticado o error 401
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = loginService.login(request.getEmail(), request.getPassword());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return ResponseEntity.ok(new LoginResponse("Exitoso", user.getUserId()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Credenciales inválidas", -1L));
        }
    }

    /**
     * Maneja la petición de registro de nuevos usuarios.
     * <p>
     * Recibe un objeto {@link RegisterRequest} con los datos necesarios,
     * llama a {@link RegisterService#register} para registrar al usuario y
     * devuelve un {@link RegisterResponse} con el mensaje de resultado.
     *
     * @param request Objeto con datos para registrar un usuario
     * @return Respuesta HTTP con mensaje indicando éxito o motivo de fallo
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        String resultado = registerService.register(
                request.getName(),
                request.getLastname(),
                request.getEmail(),
                request.getPassword());

        return ResponseEntity.ok(new RegisterResponse(resultado));
    }
}
