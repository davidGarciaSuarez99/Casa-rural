package casabaju.repository;

import casabaju.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por email (para el login)
    Optional<Usuario> findByEmail(String email);

    // Comprobar si ya existe un email registrado
    boolean existsByEmail(String email);
}
