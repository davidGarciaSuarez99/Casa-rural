package casabaju.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reserva_id", nullable = false, unique = true)
    private Reserva reserva;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Metodo metodo;

    @Column(name = "id_transaccion", length = 255)
    private String idTransaccion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal importe;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado = Estado.PENDIENTE;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    @Column(name = "creado_en", nullable = false, updatable = false)
    private LocalDateTime creadoEn = LocalDateTime.now();

    public enum Metodo {
        STRIPE, PAYPAL, TRANSFERENCIA
    }

    public enum Estado {
        PENDIENTE, COMPLETADO, FALLIDO, REEMBOLSADO
    }
}