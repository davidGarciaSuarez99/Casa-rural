package casabaju.service;

import casabaju.model.Resena;
import casabaju.repository.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    // Reseñas aprobadas para mostrar en la web
    public List<Resena> obtenerAprobadas() {
        return resenaRepository.findByAprobadaTrue();
    }

    // Reseñas pendientes de aprobar (panel admin)
    public List<Resena> obtenerPendientes() {
        return resenaRepository.findByAprobadaFalse();
    }

    // Crear nueva reseña (queda pendiente de aprobación)
    public Resena crear(Resena resena) {
        resena.setAprobada(false);
        return resenaRepository.save(resena);
    }

    // Aprobar una reseña (solo admin)
    public Resena aprobar(Long id) {
        Resena resena = resenaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
        resena.setAprobada(true);
        return resenaRepository.save(resena);
    }

    // Eliminar una reseña (solo admin)
    public void eliminar(Long id) {
        resenaRepository.deleteById(id);
    }
}
