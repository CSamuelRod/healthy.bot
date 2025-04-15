package app.healthy.bot.dto;

import lombok.Data;

@Data

public class AuthResponse {

    private String message;

    public AuthResponse(String message) {
        this.message = message;

    }

    public AuthResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
