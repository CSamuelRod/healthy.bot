package app.healthy.bot.dto;

public class LoginResponse {

    private String message;
    private Long userId;

    // Constructor adecuado para el mensaje y el userId
    public LoginResponse(String message, Long userId) {
        this.message = message;
        this.userId = userId;
    }

    // Getters y setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
