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

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RegisterService registerService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = loginService.login(request.getEmail(), request.getPassword());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return ResponseEntity.ok(new LoginResponse("Exitoso", user.getUserId()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Credenciales inválidas", (long) -1));
        }
    }

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
