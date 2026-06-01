package casabaju.repository;

import casabaju.model.Alojamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlojamientoRepository extends JpaRepository<Alojamiento, Long> {

    // Solo los alojamientos activos (para mostrar en la web)
    List<Alojamiento> findByActivoTrue();

    // Buscar por capacidad mínima (para el filtro de búsqueda)
    List<Alojamiento> findByCapacidadGreaterThanEqualAndActivoTrue(Integer capacidad);
}
