package app.healthy.bot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private boolean cumplida;

    @ManyToOne
    @JoinColumn(name = "habito_id", nullable = false)
    private Habito habito;

}
