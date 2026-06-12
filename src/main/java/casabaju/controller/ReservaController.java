package casabaju.controller;

import casabaju.model.Alojamiento;
import casabaju.model.Reserva;
import casabaju.model.Usuario;
import casabaju.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*", allowedHeaders = {"Authorization", "Content-Type", "Accept"})
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // GET /api/reservas
    // Devuelve todas las reservas del sistema. Pensado para el panel de administración.
    @GetMapping
    public List<Reserva> obtenerTodas() {
        System.out.println("Obteniendo reservas");
        return reservaService.obtenerTodas();
    }

    // GET /api/reservas/{id}
    // Devuelve una reserva concreta por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerPorId(@PathVariable Long id) {
        return reservaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/reservas/usuario/{usuarioId}
    // Devuelve todas las reservas de un usuario específico.
    // Se usa en "Mis reservas" del cliente.
    @GetMapping("/usuario/{usuarioId}")
    public List<Reserva> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return reservaService.obtenerPorUsuario(usuarioId);
    }

    // GET /api/reservas/disponibilidad?alojamientoId=1&entrada=...&salida=...
    // Comprueba si un alojamiento está libre en el rango de fechas indicado.
    // Devuelve true o false.
    @GetMapping("/disponibilidad")
    public ResponseEntity<Boolean> comprobarDisponibilidad(
            @RequestParam Long alojamientoId,
            @RequestParam LocalDate entrada,
            @RequestParam LocalDate salida) {
        boolean disponible = reservaService.estaDisponible(alojamientoId, entrada, salida);
        return ResponseEntity.ok(disponible);
    }

    // POST /api/reservas
    // Crea una nueva reserva. Primero comprueba disponibilidad y, si hay hueco,
    // guarda la reserva con estado PENDIENTE.
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        try {
            Reserva reserva = new Reserva();

            Alojamiento aloj = new Alojamiento();
            aloj.setId(Long.parseLong(((Map)body.get("alojamiento")).get("id").toString()));
            reserva.setAlojamiento(aloj);

            Usuario usuario = new Usuario();
            usuario.setId(Long.parseLong(((Map)body.get("usuario")).get("id").toString()));
            reserva.setUsuario(usuario);

            reserva.setFechaEntrada(LocalDate.parse(body.get("fechaEntrada").toString()));
            reserva.setFechaSalida(LocalDate.parse(body.get("fechaSalida").toString()));
            reserva.setNumPersonas(Integer.parseInt(body.get("numPersonas").toString()));
            reserva.setPrecioTotal(new java.math.BigDecimal(body.get("precioTotal").toString()));
            reserva.setNotasCliente(body.getOrDefault("notasCliente", "").toString());
            reserva.setEstado(Reserva.Estado.PENDIENTE);

            Reserva nueva = reservaService.crear(reserva);
            return ResponseEntity.ok(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/reservas/{id}/estado
    // Cambia el estado de una reserva (PENDIENTE, CONFIRMADA, COMPLETADA, CANCELADA).
    // Solo para administradores.
    @PutMapping("/{id}/estado")
    public ResponseEntity<Reserva> cambiarEstado(@PathVariable Long id,
                                                  @RequestParam Reserva.Estado estado) {
        return ResponseEntity.ok(reservaService.cambiarEstado(id, estado));
    }

    // DELETE /api/reservas/{id}
    // Cancela una reserva cambiando su estado a CANCELADA. No la borra de la base de datos.
    @DeleteMapping("/{id}")
    public ResponseEntity<Reserva> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelar(id));
    }
}
