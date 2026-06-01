package casabaju.repository;

import casabaju.model.Galeria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GaleriaRepository extends JpaRepository<Galeria, Long> {

    // Fotos de un alojamiento concreto ordenadas por posición
    List<Galeria> findByAlojamientoIdOrderByOrdenAsc(Long alojamientoId);

    // Solo las fotos de portada
    List<Galeria> findByEsPortadaTrue();
}
