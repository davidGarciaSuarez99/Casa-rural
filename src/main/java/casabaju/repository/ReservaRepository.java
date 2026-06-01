package casabaju.repository;

import casabaju.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    // Reservas de un usuario concreto
    List<Reserva> findByUsuarioId(Long usuarioId);

    // Reservas de un alojamiento concreto
    List<Reserva> findByAlojamientoId(Long alojamientoId);

    // Reservas por estado (PENDIENTE, CONFIRMADA, etc.)
    List<Reserva> findByEstado(Reserva.Estado estado);

    // Comprobar si un alojamiento está disponible en un rango de fechas
    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.alojamiento.id = :alojamientoId " +
           "AND r.estado != 'CANCELADA' " +
           "AND r.fechaEntrada < :fechaSalida AND r.fechaSalida > :fechaEntrada")
    boolean existeSolapamiento(@Param("alojamientoId") Long alojamientoId,
                               @Param("fechaEntrada") LocalDate fechaEntrada,
                               @Param("fechaSalida") LocalDate fechaSalida);
}
