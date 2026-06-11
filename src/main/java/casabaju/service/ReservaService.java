package casabaju.service;

import casabaju.model.Reserva;
import casabaju.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    // Obtener todas las reservas (para el admin)
    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    // Obtener reservas de un usuario concreto
    public List<Reserva> obtenerPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    // Obtener reservas por estado
    public List<Reserva> obtenerPorEstado(Reserva.Estado estado) {
        return reservaRepository.findByEstado(estado);
    }

    private static final Long ID_CASA_COMPLETA = 1L;

    public boolean estaDisponible(Long alojamientoId, LocalDate entrada, LocalDate salida) {
        // Si se quiere reservar la Casa Completa, no puede haber NINGUNA reserva activa
        if (alojamientoId.equals(ID_CASA_COMPLETA)) {
            return !reservaRepository.existeCualquierSolapamiento(entrada, salida);
        }

        // Si se quiere reservar una habitación, no puede estar ocupada NI la Casa Completa
        boolean habitacionOcupada = reservaRepository.existeSolapamiento(alojamientoId, entrada, salida);
        boolean casaCompletaOcupada = reservaRepository.existeSolapamiento(ID_CASA_COMPLETA, entrada, salida);

        return !habitacionOcupada && !casaCompletaOcupada;
    }

    // Crear una nueva reserva
    public Reserva crear(Reserva reserva) {
        // Verificar disponibilidad antes de guardar
        if (!estaDisponible(reserva.getAlojamiento().getId(),
                            reserva.getFechaEntrada(),
                            reserva.getFechaSalida())) {
            throw new RuntimeException("El alojamiento no está disponible en esas fechas");
        }
        return reservaRepository.save(reserva);
    }

    // Cambiar el estado de una reserva
    public Reserva cambiarEstado(Long id, Reserva.Estado nuevoEstado) {
        Reserva reserva = reservaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        reserva.setEstado(nuevoEstado);
        return reservaRepository.save(reserva);
    }

    // Cancelar una reserva
    public Reserva cancelar(Long id) {
        return cambiarEstado(id, Reserva.Estado.CANCELADA);
    }

    // Obtener reserva por ID
    public Optional<Reserva> obtenerPorId(Long id) {
        return reservaRepository.findById(id);
    }
}
