package casabaju.controller;

import casabaju.model.Resena;
import casabaju.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@CrossOrigin(origins = "*")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    // GET /api/resenas → reseñas aprobadas (web pública)
    @GetMapping
    public List<Resena> obtenerAprobadas() {
        return resenaService.obtenerAprobadas();
    }

    // GET /api/resenas/pendientes → reseñas pendientes (admin)
    @GetMapping("/pendientes")
    public List<Resena> obtenerPendientes() {
        return resenaService.obtenerPendientes();
    }

    // POST /api/resenas → crear nueva reseña
    @PostMapping
    public ResponseEntity<Resena> crear(@RequestBody Resena resena) {
        return ResponseEntity.ok(resenaService.crear(resena));
    }

    // PUT /api/resenas/1/aprobar → aprobar reseña (admin)
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Resena> aprobar(@PathVariable Long id) {
        return ResponseEntity.ok(resenaService.aprobar(id));
    }

    // DELETE /api/resenas/1 → eliminar reseña (admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        resenaService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}
