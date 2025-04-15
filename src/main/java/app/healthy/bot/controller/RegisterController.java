package app.healthy.bot.controller;



import app.healthy.bot.dto.RegisterRequest;
import app.healthy.bot.dto.RegisterResponse;
import app.healthy.bot.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/register")
@CrossOrigin
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        String resultado = registerService.register(
                request.getName(),
                request.getLastname(),
                request.getEmail(),
                request.getPassword());
        // Return JSON with proper content type
        return ResponseEntity.ok(new RegisterResponse(resultado));
    }
}