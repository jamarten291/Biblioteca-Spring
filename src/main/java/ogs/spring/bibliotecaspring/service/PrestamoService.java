package ogs.spring.bibliotecaspring.service;

import ogs.spring.bibliotecaspring.entity.Prestamo;
import ogs.spring.bibliotecaspring.entity.Socio;
import ogs.spring.bibliotecaspring.repository.PrestamoRepository;
import org.springframework.stereotype.Service;

@Service
class PrestamoService {
    private PrestamoRepository prestamoRepository;

    public PrestamoService(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public Prestamo crearPrestamo(Long socioId, Long libroId) {
        return new Prestamo();
    }

    public Prestamo devolverPrestamo(Long id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    public void comprobarReglasPrestamo(Prestamo prestamo) {

    }

    public void calcularFechasYPenalizaciones(Prestamo prestamo) {

    }
}
