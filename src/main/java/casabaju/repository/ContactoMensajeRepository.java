package casabaju.repository;

import casabaju.model.ContactoMensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactoMensajeRepository extends JpaRepository<ContactoMensaje, Long> {

    // Mensajes no leídos (para el panel de admin)
    List<ContactoMensaje> findByLeidoFalse();
}
