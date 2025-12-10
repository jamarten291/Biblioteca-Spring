package ogs.spring.bibliotecaspring.service;

import ogs.spring.bibliotecaspring.entity.EstadoSocio;
import ogs.spring.bibliotecaspring.entity.Libro;
import ogs.spring.bibliotecaspring.entity.Prestamo;
import ogs.spring.bibliotecaspring.entity.Socio;
import ogs.spring.bibliotecaspring.repository.LibroRepository;
import ogs.spring.bibliotecaspring.repository.PrestamoRepository;
import ogs.spring.bibliotecaspring.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
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
        Socio socioAsociado = socioRepository.findById(socioId).orElseThrow();
        Libro libroAsociado = libroRepository.findById(libroId).orElseThrow();
        Prestamo p = new Prestamo();

        if (socioAsociado.getEstado().equals(EstadoSocio.SANCIONADO)) {
            long diffDiasSancion = Duration.between(
                    LocalDate.now(),
                    socioAsociado.getFechaFinPenalizacion()
            ).toDays();

            // Si la diferencia de dias es negativa significa que ya ha pasado su periodo de penalización
            if (diffDiasSancion < 0) {
                socioAsociado.setEstado(EstadoSocio.ACTIVO);
            }
        }

        // Si aún no se ha pasado su periodo de penalización, no entra a este if
        if (socioAsociado.getNumPrestamos() < 3 &&
                !socioAsociado.getEstado().equals(EstadoSocio.SANCIONADO)) {
            p.setSocio(socioAsociado);
            p.setLibro(libroAsociado);

            LocalDate fechaLimite = p.getFechaPrestamo().plusDays(2);
            p.setFechaLimite(fechaLimite);
        }

        return prestamoRepository.save(p);
    }

    public Prestamo devolverPrestamo(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id).orElseThrow();
        LocalDate fechaActual = LocalDate.now();

        prestamo.setFechaDevolucion(fechaActual);
        long diff = Duration.between(
                prestamo.getFechaDevolucion(),
                prestamo.getFechaLimite()
        ).toDays();

        // Si la diferencia de días es positiva o cero, la entrega ha sido a tiempo
        if (diff >= 0) {
            prestamo.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);
        } else {
            // Convierto la diferencia a positivo y multiplico por dos para calcular la penalización
            long penalizacion = Math.abs(diff) * 2;
            prestamo.setEstado(Prestamo.EstadoPrestamo.ATRASADO);

            Socio socioAsociado = prestamo.getSocio();
            socioAsociado.setEstado(EstadoSocio.SANCIONADO);
            socioAsociado.setFechaFinPenalizacion(fechaActual.plusDays(penalizacion));
        }

        return prestamo;
    }

    public void comprobarReglasPrestamo(Prestamo prestamo) {

    }

    public void calcularFechasYPenalizaciones(Prestamo prestamo) {

    }

    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }
}
