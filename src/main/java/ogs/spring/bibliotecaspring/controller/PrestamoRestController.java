package ogs.spring.bibliotecaspring.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import ogs.spring.bibliotecaspring.entity.Prestamo;
import ogs.spring.bibliotecaspring.repository.PrestamoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PrestamoRestController {

    private final PrestamoRepository prestamoRepository;

    public PrestamoRestController(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    // ── CREATE ────────────────────────────────────────────────────────
    @PostMapping("/prestamos")
    public ResponseEntity<Prestamo> crearPrestamo(@Valid @RequestBody Prestamo prestamo) {
        // El constructor de Prestamo ya inicializa fechaPrestamo, estado y diasRetraso
        Prestamo creado = prestamoRepository.save(prestamo);
        return ResponseEntity.ok(creado);
    }

    // ── READ ALL ──────────────────────────────────────────────────────
    @GetMapping("/prestamos")
    public ResponseEntity<List<Prestamo>> listarPrestamos() {
        return ResponseEntity.ok(prestamoRepository.findAll());
    }

    // ── READ ONE ──────────────────────────────────────────────────────
    @GetMapping("/prestamos/{id}")
    public ResponseEntity<Prestamo> obtenerPrestamo(@PathVariable Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Prestamo no encontrado con ID: " + id));
        return ResponseEntity.ok(prestamo);
    }

    // ── UPDATE ────────────────────────────────────────────────────────
    @PutMapping("/prestamos/{id}")
    public ResponseEntity<Prestamo> actualizarPrestamo(
            @PathVariable Long id,
            @Valid @RequestBody Prestamo datosActualizados) {

        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Prestamo no encontrado con ID: " + id));

        // Sólo se actualizan los campos que pueden modificarse
        prestamo.setLibro(datosActualizados.getLibro());
        prestamo.setSocio(datosActualizados.getSocio());
        prestamo.setFechaLimite(datosActualizados.getFechaLimite());
        prestamo.setFechaDevolucion(datosActualizados.getFechaDevolucion());
        prestamo.setDiasRetraso(datosActualizados.getDiasRetraso());
        prestamo.setEstado(datosActualizados.getEstado());

        Prestamo actualizado = prestamoRepository.save(prestamo);
        return ResponseEntity.ok(actualizado);
    }

    // ── DELETE ────────────────────────────────────────────────────────
    @DeleteMapping("/prestamos/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable Long id) {
        if (!prestamoRepository.existsById(id)) {
            throw new EntityNotFoundException("Prestamo no encontrado con ID: " + id);
        }
        prestamoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── OPERACIONES ADICIONALES ───────────────────────────────────────
    /**
     * Marca el préstamo como devuelto y calcula días de retraso.
     * Se espera que la fecha de devolución sea enviada en el cuerpo.
     */
    @PatchMapping("/prestamos/{id}/devolver")
    public ResponseEntity<Prestamo> devolverPrestamo(
            @PathVariable Long id,
            @RequestBody LocalDate fechaDevolucion) {

        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Prestamo no encontrado con ID: " + id));

        prestamo.setFechaDevolucion(fechaDevolucion);
        // cálculo simple de retraso
        if (fechaDevolucion.isAfter(prestamo.getFechaLimite())) {
            int dias = (int) java.time.temporal.ChronoUnit.DAYS.between(
                    prestamo.getFechaLimite(), fechaDevolucion);
            prestamo.setDiasRetraso(dias);
            prestamo.setEstado(Prestamo.EstadoPrestamo.ATRASADO);
        } else {
            prestamo.setDiasRetraso(0);
            prestamo.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);
        }

        Prestamo guardado = prestamoRepository.save(prestamo);
        return ResponseEntity.ok(guardado);
    }
}
