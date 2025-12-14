package ogs.spring.bibliotecaspring.service;

import jakarta.persistence.EntityNotFoundException;
import ogs.spring.bibliotecaspring.entity.Libro;
import ogs.spring.bibliotecaspring.entity.Prestamo;
import ogs.spring.bibliotecaspring.entity.Socio;
import ogs.spring.bibliotecaspring.exception.LoanNotAllowedException;
import ogs.spring.bibliotecaspring.repository.LibroRepository;
import ogs.spring.bibliotecaspring.repository.PrestamoRepository;
import ogs.spring.bibliotecaspring.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PrestamoService {
    private PrestamoRepository prestamoRepository;
    private SocioRepository socioRepository;
    private LibroRepository libroRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
                           SocioRepository socioRepository,
                           LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.socioRepository = socioRepository;
        this.libroRepository = libroRepository;
    }

    public Prestamo crearPrestamo(Long socioId, Long libroId) {
        Prestamo p = new Prestamo();

        Socio socioAsociado = socioRepository.findById(socioId)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo encontrar el socio."));
        Libro libroAsociado = libroRepository.findById(libroId)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo encontrar el libro."));
        p.setSocio(socioAsociado);
        p.setLibro(libroAsociado);
        comprobarReglasPrestamo(p);

        return prestamoRepository.save(p);
    }

    public Prestamo devolverPrestamo(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se pudo encontrar el préstamo"));

        prestamo.setFechaDevolucion(LocalDate.now());
        calcularFechasYPenalizaciones(prestamo);

        return prestamoRepository.save(prestamo);
    }

    public void comprobarReglasPrestamo(Prestamo prestamo) {
        Socio socioAsociado = prestamo.getSocio();

        if (socioAsociado.getEstado().equals(Socio.EstadoSocio.SANCIONADO)) {
            long diffDiasSancion = ChronoUnit.DAYS.between(
                    LocalDate.now(),
                    socioAsociado.getFechaFinPenalizacion()
            );

            // Si la diferencia de dias es negativa significa que ya ha pasado su periodo de penalización
            if (diffDiasSancion < 0) {
                socioAsociado.setEstado(Socio.EstadoSocio.ACTIVO);
            }
        }

        // Si aún no se ha pasado su periodo de penalización, no entra a este if
        if (socioAsociado.getNumPrestamosActivos() < 3 &&
                !socioAsociado.getEstado().equals(Socio.EstadoSocio.SANCIONADO)) {
            LocalDate fechaLimite = prestamo.getFechaPrestamo().plusDays(2);
            prestamo.setFechaLimite(fechaLimite);
        } else {
            throw new LoanNotAllowedException();
        }
    }

    public void calcularFechasYPenalizaciones(Prestamo prestamo) {
        long diff = ChronoUnit.DAYS.between(
                prestamo.getFechaDevolucion(),
                prestamo.getFechaLimite()
        );

        // Si la diferencia de días es positiva o cero, la entrega ha sido a tiempo
        if (diff >= 0) {
            prestamo.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);
        } else {
            // Convierto la diferencia a positivo y multiplico por dos para calcular la penalización
            long diasRetraso = Math.abs(diff);
            long penalizacion = diasRetraso * 2;
            prestamo.setEstado(Prestamo.EstadoPrestamo.ATRASADO);
            prestamo.setDiasRetraso(Math.toIntExact(diasRetraso));

            Socio socioAsociado = prestamo.getSocio();
            socioAsociado.setEstado(Socio.EstadoSocio.SANCIONADO);
            socioAsociado.setFechaFinPenalizacion(LocalDate.now().plusDays(penalizacion));
        }
    }

    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    public Prestamo obtenerPrestamoPorId(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("No se pudo encontrar el préstamo con ID: " + id));
    }

    public void eliminarPrestamo(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("No se pudo encontrar el préstamo con ID: " + id));

        // Regla opcional: solo permitir eliminar préstamos devueltos
        if (prestamo.getEstado() == Prestamo.EstadoPrestamo.ACTIVO) {
            throw new IllegalStateException("No se puede eliminar un préstamo activo");
        }

        prestamoRepository.delete(prestamo);
    }

    public List<Prestamo> findByEstado(Prestamo.EstadoPrestamo estado) {
        return prestamoRepository.findByEstado(estado);
    }
}
