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
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // GET /api/reservas → todas las reservas (admin)
    @GetMapping
    public List<Reserva> obtenerTodas() {
        return reservaService.obtenerTodas();
    }

    // GET /api/reservas/1 → reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerPorId(@PathVariable Long id) {
        return reservaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/reservas/usuario/1 → reservas de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<Reserva> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return reservaService.obtenerPorUsuario(usuarioId);
    }

    // GET /api/reservas/disponibilidad?alojamientoId=1&entrada=2025-07-01&salida=2025-07-05
    @GetMapping("/disponibilidad")
    public ResponseEntity<Boolean> comprobarDisponibilidad(
            @RequestParam Long alojamientoId,
            @RequestParam LocalDate entrada,
            @RequestParam LocalDate salida) {
        boolean disponible = reservaService.estaDisponible(alojamientoId, entrada, salida);
        return ResponseEntity.ok(disponible);
    }

    // POST /api/reservas → crear nueva reserva
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

    // PUT /api/reservas/1/estado → cambiar estado (admin)
    @PutMapping("/{id}/estado")
    public ResponseEntity<Reserva> cambiarEstado(@PathVariable Long id,
                                                  @RequestParam Reserva.Estado estado) {
        return ResponseEntity.ok(reservaService.cambiarEstado(id, estado));
    }

    // DELETE /api/reservas/1 → cancelar reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Reserva> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelar(id));
    }
}
