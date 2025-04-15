package app.healthy.bot.controller;



import app.healthy.bot.dto.AuthRequest;
import app.healthy.bot.dto.AuthResponse;
import app.healthy.bot.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ResponseBody  // Add this to tell Spring to convert the response to JSON
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        boolean valido = authService.login(request.getEmail(), request.getPassword());

        if (valido) {
            return ResponseEntity.ok(new AuthResponse("Inicio de sesión exitoso"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Credenciales inválidas"));
        }
    }
}
