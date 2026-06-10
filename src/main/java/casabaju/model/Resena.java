package casabaju.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "reserva_id", nullable = false, unique = true)
    private Reserva reserva;

    @Column(nullable = false)
    private Integer puntuacion; // 1-5 estrellas

    @Column(length = 120)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(nullable = false)
    private Boolean aprobada = false; // el admin la aprueba antes de publicarse

    @CreationTimestamp
    @Column(name = "creada_en", nullable = false, updatable = false)
    private LocalDateTime creadaEn;
}
