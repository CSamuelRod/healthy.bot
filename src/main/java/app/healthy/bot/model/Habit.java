package app.healthy.bot.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "habits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long habitId;

    private String name;

    private String description;

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL)
    private List<Goal> goals;

    private Boolean isCustom = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
}