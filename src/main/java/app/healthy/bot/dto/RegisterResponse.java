package app.healthy.bot.dto;

import lombok.Data;

@Data

public class RegisterResponse {
    private String message;


    public RegisterResponse(String message) {
        this.message = message;
    }

    public RegisterResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}