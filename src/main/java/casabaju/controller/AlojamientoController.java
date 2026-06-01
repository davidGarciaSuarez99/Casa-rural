package casabaju.controller;

import casabaju.model.Alojamiento;
import casabaju.service.AlojamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alojamientos")
@CrossOrigin(origins = "*")
public class AlojamientoController {

    @Autowired
    private AlojamientoService alojamientoService;

    // GET /api/alojamientos → lista todos los alojamientos activos
    @GetMapping
    public List<Alojamiento> obtenerTodos() {
        return alojamientoService.obtenerTodos();
    }

    // GET /api/alojamientos/1 → obtiene un alojamiento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Alojamiento> obtenerPorId(@PathVariable Long id) {
        return alojamientoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/alojamientos/buscar?personas=4 → filtra por capacidad
    @GetMapping("/buscar")
    public List<Alojamiento> buscarPorCapacidad(@RequestParam Integer personas) {
        return alojamientoService.buscarPorCapacidad(personas);
    }

    // POST /api/alojamientos → crea un nuevo alojamiento (solo admin)
    @PostMapping
    public Alojamiento crear(@RequestBody Alojamiento alojamiento) {
        return alojamientoService.guardar(alojamiento);
    }

    // PUT /api/alojamientos/1 → actualiza un alojamiento (solo admin)
    @PutMapping("/{id}")
    public ResponseEntity<Alojamiento> actualizar(@PathVariable Long id,
                                                   @RequestBody Alojamiento alojamiento) {
        return alojamientoService.obtenerPorId(id).map(a -> {
            alojamiento.setId(id);
            return ResponseEntity.ok(alojamientoService.guardar(alojamiento));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/alojamientos/1 → desactiva un alojamiento (solo admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        alojamientoService.desactivar(id);
        return ResponseEntity.ok().build();
    }
}
