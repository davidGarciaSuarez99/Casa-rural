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

    // GET /api/alojamientos
    // Devuelve todos los alojamientos que están activos (no desactivados por el admin).
    @GetMapping
    public List<Alojamiento> obtenerTodos() {
        return alojamientoService.obtenerTodos();
    }

    // GET /api/alojamientos/{id}
    // Busca un alojamiento concreto por su ID. Si no existe, devuelve un 404.
    @GetMapping("/{id}")
    public ResponseEntity<Alojamiento> obtenerPorId(@PathVariable Long id) {
        return alojamientoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // GET /api/alojamientos/buscar?personas=4
    // Filtra los alojamientos activos que tengan capacidad para el número de personas indicado.
    @GetMapping("/buscar")
    public List<Alojamiento> buscarPorCapacidad(@RequestParam Integer personas) {
        return alojamientoService.buscarPorCapacidad(personas);
    }

    // POST /api/alojamientos
    // Crea un nuevo alojamiento con los datos recibidos. Solo para administradores.
    @PostMapping
    public Alojamiento crear(@RequestBody Alojamiento alojamiento) {
        return alojamientoService.guardar(alojamiento);
    }

    // PUT /api/alojamientos/{id}
    // Actualiza los datos de un alojamiento existente. Si no existe, devuelve 404.
    @PutMapping("/{id}")
    public ResponseEntity<Alojamiento> actualizar(@PathVariable Long id,
                                                   @RequestBody Alojamiento alojamiento) {
        return alojamientoService.obtenerPorId(id).map(a -> {
            alojamiento.setId(id);
            return ResponseEntity.ok(alojamientoService.guardar(alojamiento));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/alojamientos/{id}
    // No borra el alojamiento de la base de datos, solo lo marca como inactivo
    // para que deje de aparecer en la web.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        alojamientoService.desactivar(id);
        return ResponseEntity.ok().build();
    }
}
