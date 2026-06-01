package casabaju.service;

import casabaju.model.Alojamiento;
import casabaju.repository.AlojamientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlojamientoService {

    @Autowired
    private AlojamientoRepository alojamientoRepository;

    // Obtener todos los alojamientos activos
    public List<Alojamiento> obtenerTodos() {
        return alojamientoRepository.findByActivoTrue();
    }

    // Obtener un alojamiento por ID
    public Optional<Alojamiento> obtenerPorId(Long id) {
        return alojamientoRepository.findById(id);
    }

    // Buscar por capacidad mínima
    public List<Alojamiento> buscarPorCapacidad(Integer personas) {
        return alojamientoRepository.findByCapacidadGreaterThanEqualAndActivoTrue(personas);
    }

    // Guardar o actualizar un alojamiento (solo admin)
    public Alojamiento guardar(Alojamiento alojamiento) {
        return alojamientoRepository.save(alojamiento);
    }

    // Desactivar un alojamiento en lugar de borrarlo
    public void desactivar(Long id) {
        alojamientoRepository.findById(id).ifPresent(a -> {
            a.setActivo(false);
            alojamientoRepository.save(a);
        });
    }
}
