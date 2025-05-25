package app.healthy.bot.dto;

import app.healthy.bot.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private LocalDate registrationDate;

    // De entidad a DTO
    public static UserDto fromEntity(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getUserId());
        dto.setName(user.getName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRegistrationDate(user.getRegistrationDate());
        return dto;
    }

    // De DTO a entidad (sin password)
    public User toEntity(String encodedPassword) {
        User user = new User();
        user.setUserId(this.id);
        user.setName(this.name);
        user.setLastName(this.lastName);
        user.setEmail(this.email);
        user.setPassword(encodedPassword); // necesario pasarla como argumento
        user.setRegistrationDate(this.registrationDate != null ? this.registrationDate : LocalDate.now());
        return user;
    }

    // Alternativa directa para usar sin DTO explícito
    public static User fromRaw(String name, String lastName, String email, String encodedPassword) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRegistrationDate(LocalDate.now());
        return user;
    }

}
