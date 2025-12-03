package ogs.spring.bibliotecaspring.controller;

import ogs.spring.bibliotecaspring.entity.Prestamo;
import ogs.spring.bibliotecaspring.repository.PrestamoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BibliotecaRestController {


    private final PrestamoRepository prestamoRepository;

    public BibliotecaRestController(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @DeleteMapping("/prestamos/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable Long id) {
        prestamoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/prestamos/{id}")
    public ResponseEntity<Prestamo> obtenerPrestamo(@PathVariable Long id) {
        // Optional es un contenedor que puede o no contener un valor nulo
        Optional<Prestamo> prestamo = prestamoRepository.findById(id);

        // Si el valor del optional es nulo, significa que no existe el prestamo
        return prestamo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
