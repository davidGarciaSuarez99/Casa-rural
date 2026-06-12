package casabaju.controller;

import casabaju.model.ContactoMensaje;
import casabaju.service.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacto")
@CrossOrigin(origins = "*")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    // Inyectamos el componente nativo de Spring para enviar correos
    @Autowired
    private JavaMailSender mailSender;

    // POST /api/contacto
    // Guarda un mensaje nuevo enviado desde el formulario de contacto de la web.
    @PostMapping
    public ResponseEntity<ContactoMensaje> enviar(@RequestBody ContactoMensaje mensaje) {
        return ResponseEntity.ok(contactoService.guardar(mensaje));
    }

    // GET /api/contacto
    // Devuelve todos los mensajes de contacto. Solo para el panel de administración.
    @GetMapping
    public List<ContactoMensaje> obtenerTodos() {
        return contactoService.obtenerTodos();
    }

    // GET /api/contacto/noleidos
    // Devuelve solo los mensajes que aún no han sido leídos por el administrador.
    // Se usa para mostrar el badge de notificaciones en el panel.
    @GetMapping("/noleidos")
    public List<ContactoMensaje> obtenerNoLeidos() {
        return contactoService.obtenerNoLeidos();
    }

    // PUT /api/contacto/{id}/leido
    // Marca un mensaje como leído en la base de datos.
    @PutMapping("/{id}/leido")
    public ResponseEntity<ContactoMensaje> marcarLeido(@PathVariable Long id) {
        return ResponseEntity.ok(contactoService.marcarLeido(id));
    }

    // PUT /api/contacto/{id}/responder
    // Busca el mensaje original, obtiene el email del cliente y le envía
    // un correo real con el asunto y texto escritos por el admin en el panel.
    // Después marca el mensaje como leído automáticamente.
    @PutMapping("/{id}/responder")
    public ResponseEntity<?> responderMensaje(@PathVariable Long id, @RequestBody RespuestaRequest request) {
        try {
            // 1. Buscamos el mensaje original en la base de datos usando el ID para saber quién nos escribió
            ContactoMensaje mensajeOriginal = contactoService.obtenerPorId(id);
            if (mensajeOriginal == null) {
                return ResponseEntity.status(404).body("No se encontró el mensaje original con ID: " + id);
            }

            // 2. Conseguimos el correo real del cliente que escribió a través de la página
            String emailDelCliente = mensajeOriginal.getEmail();

            // 3. Configuramos el email de salida
            SimpleMailMessage mail = new SimpleMailMessage();

            // El destinatario es el CLIENTE, no tú
            mail.setTo(emailDelCliente);

            // El asunto y el cuerpo del correo es lo que tú acabas de escribir en el panel de administración
            mail.setSubject(request.getAsunto());
            mail.setText(request.getMensaje());

            // 4. Se ejecuta el envío a través de tu SMTP (Mailtrap o Gmail)
            mailSender.send(mail);

            // 5. Marcamos el mensaje como leído en tu base de datos
            contactoService.marcarLeido(id);

            return ResponseEntity.ok().body("{\"status\":\"OK\",\"message\":\"Respuesta enviada con éxito al cliente\"}");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno al enviar el correo: " + e.getMessage());
        }
    }

    // Clase auxiliar interna (DTO) para recibir el cuerpo JSON del frontend de forma limpia
    public static class RespuestaRequest {
        private String email;
        private String asunto;
        private String mensaje;

        // Getters y Setters necesarios para que Spring Jackson procese el JSON
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getAsunto() { return asunto; }
        public void setAsunto(String asunto) { this.asunto = asunto; }

        public String getMensaje() { return mensaje; }
        public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    }
}