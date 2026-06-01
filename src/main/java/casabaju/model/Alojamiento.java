package casabaju.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "alojamientos")
public class Alojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(name = "num_camas", nullable = false)
    private Integer numCamas = 1;

    @Column(precision = 6, scale = 2)
    private BigDecimal metros2;

    @Column(name = "precio_noche", nullable = false, precision = 8, scale = 2)
    private BigDecimal precioNoche;

    @Column(nullable = false)
    private Boolean activo = true;

    // Relación con servicios (N:M)
    @ManyToMany
    @JoinTable(
            name = "alojamiento_servicio",
            joinColumns = @JoinColumn(name = "alojamiento_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_id")
    )
    private List<Servicio> servicios;

    @JsonIgnore
    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL)
    private List<Tarifa> tarifas;

    @JsonIgnore
    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL)
    private List<Reserva> reservas;

    @JsonIgnore
    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL)
    private List<Galeria> fotos;
}
