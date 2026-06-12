package casabaju.controller;

import casabaju.model.Resena;
import casabaju.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@CrossOrigin(origins = "*")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    // GET /api/resenas
    // Devuelve solo las reseñas aprobadas por el admin. Son las que se muestran en la web pública.
    @GetMapping
    public List<Resena> obtenerAprobadas() {
        return resenaService.obtenerAprobadas();
    }

    // GET /api/resenas/pendientes
    // Devuelve las reseñas que están esperando ser revisadas por el administrador.
    @GetMapping("/pendientes")
    public List<Resena> obtenerPendientes() {
        return resenaService.obtenerPendientes();
    }

    // POST /api/resenas
    // Crea una nueva reseña vinculada a una reserva completada.
    // Si ya existe una reseña para esa reserva, devuelve un error 409.
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Resena resena) {
        try {
            return ResponseEntity.ok(resenaService.crear(resena));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    // PUT /api/resenas/{id}/aprobar
    // El administrador aprueba una reseña para que sea visible en la web pública.
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<Resena> aprobar(@PathVariable Long id) {
        return ResponseEntity.ok(resenaService.aprobar(id));
    }

    // DELETE /api/resenas/{id}
    // Elimina una reseña definitivamente de la base de datos. Solo para administradores.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        resenaService.eliminar(id);
        return ResponseEntity.ok().build();
    }


}
