package casabaju.controller;

import casabaju.model.ContactoMensaje;
import casabaju.service.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacto")
@CrossOrigin(origins = "*")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    // POST /api/contacto → enviar mensaje desde el formulario
    @PostMapping
    public ResponseEntity<ContactoMensaje> enviar(@RequestBody ContactoMensaje mensaje) {
        return ResponseEntity.ok(contactoService.guardar(mensaje));
    }

    // GET /api/contacto → todos los mensajes (admin)
    @GetMapping
    public List<ContactoMensaje> obtenerTodos() {
        return contactoService.obtenerTodos();
    }

    // GET /api/contacto/noleidos → mensajes sin leer (admin)
    @GetMapping("/noleidos")
    public List<ContactoMensaje> obtenerNoLeidos() {
        return contactoService.obtenerNoLeidos();
    }

    // PUT /api/contacto/1/leido → marcar como leído (admin)
    @PutMapping("/{id}/leido")
    public ResponseEntity<ContactoMensaje> marcarLeido(@PathVariable Long id) {
        return ResponseEntity.ok(contactoService.marcarLeido(id));
    }
}
