package casabaju.repository;

import casabaju.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {

    // Solo reseñas aprobadas (para mostrar en la web pública)
    List<Resena> findByAprobadaTrue();

    // Reseñas pendientes de aprobar (para el panel de admin)
    List<Resena> findByAprobadaFalse();
}
