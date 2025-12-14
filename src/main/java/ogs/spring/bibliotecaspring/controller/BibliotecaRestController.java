package ogs.spring.bibliotecaspring.controller;

import jakarta.validation.Valid;
import ogs.spring.bibliotecaspring.entity.Prestamo;
import ogs.spring.bibliotecaspring.repository.LibroRepository;
import ogs.spring.bibliotecaspring.repository.PrestamoRepository;
import ogs.spring.bibliotecaspring.repository.SocioRepository;
import ogs.spring.bibliotecaspring.service.PrestamoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BibliotecaRestController {

    private final PrestamoService prestamoService;
    private final SocioRepository socioRepository;
    private final LibroRepository libroRepository;

    public BibliotecaRestController(PrestamoService prestamoService,
                                    SocioRepository socioRepository,
                                    LibroRepository libroRepository) {
        this.prestamoService = prestamoService;
        this.socioRepository = socioRepository;
        this.libroRepository = libroRepository;
    }

    @PostMapping("/prestamos")
    public ResponseEntity<Prestamo> crearPrestamo(@Valid @RequestBody CrearPrestamoDTO dto) {
        Prestamo created = prestamoService.crearPrestamo(
                dto.getSocioId(),
                dto.getLibroId()
        );
        return ResponseEntity.ok(created);
    }

    @DeleteMapping("/prestamos/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable Long id) {
        prestamoService.eliminarPrestamo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/prestamos/{id}")
    public ResponseEntity<Prestamo> recibirPrestamo(@PathVariable Long id) {
        Prestamo p = prestamoService.obtenerPrestamoPorId(id);
        return ResponseEntity.ok(p);
    }
}
