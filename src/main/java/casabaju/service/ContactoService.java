package casabaju.service;

import casabaju.model.ContactoMensaje;
import casabaju.repository.ContactoMensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactoService {

    @Autowired
    private ContactoMensajeRepository contactoMensajeRepository;

    // Guardar un mensaje nuevo del formulario de contacto
    public ContactoMensaje guardar(ContactoMensaje mensaje) {
        if (mensaje.getLeido() == null)      mensaje.setLeido(false);
        if (mensaje.getRespondido() == null)  mensaje.setRespondido(false);
        return contactoMensajeRepository.save(mensaje);
    }

    // Obtener todos los mensajes (panel admin)
    public List<ContactoMensaje> obtenerTodos() {
        return contactoMensajeRepository.findAll();
    }

    // Obtener mensajes no leídos (panel admin)
    public List<ContactoMensaje> obtenerNoLeidos() {
        return contactoMensajeRepository.findByLeidoFalse();
    }

    // Marcar un mensaje como leído
    public ContactoMensaje marcarLeido(Long id) {
        ContactoMensaje mensaje = contactoMensajeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
        mensaje.setLeido(true);
        return contactoMensajeRepository.save(mensaje);
    }
    public ContactoMensaje obtenerPorId(Long id) {
        // findById ya viene incluido de forma nativa en JpaRepository
        return contactoMensajeRepository.findById(id).orElse(null);
    }
}
