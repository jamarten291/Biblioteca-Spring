package ogs.spring.bibliotecaspring.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import ogs.spring.bibliotecaspring.entity.Libro;
import ogs.spring.bibliotecaspring.exception.ConcurrencyConflictException;
import ogs.spring.bibliotecaspring.repository.LibroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LibroRestController {

    private final LibroRepository libroRepository;

    public LibroRestController(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    // CREATE
    @PostMapping("/libros")
    public ResponseEntity<Libro> crearLibro(@Valid @RequestBody Libro libro) {
        if (libroRepository.existsById(libro.getLibroId())) {
            throw new ConcurrencyConflictException("Ya existe un libro con el id: " + libro.getLibroId());
        }

        Libro creado = libroRepository.save(libro);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // READ ALL
    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> listarLibros() {
        return ResponseEntity.ok(libroRepository.findAll());
    }

    // READ ONE
    @GetMapping("/libros/{id}")
    public ResponseEntity<Libro> obtenerLibro(@PathVariable Long id) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Libro no encontrado con ID: " + id));
        return ResponseEntity.ok(libro);
    }

    // UPDATE
    @PutMapping("/libros/{id}")
    public ResponseEntity<Libro> actualizarLibro(
            @PathVariable Long id,
            @Valid @RequestBody Libro libroActualizado) {

        Libro libro = libroRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Libro no encontrado con ID: " + id));

        libro.setTitulo(libroActualizado.getTitulo());
        libro.setAutor(libroActualizado.getAutor());
        libro.setYearPublicacion(libroActualizado.getYearPublicacion());
        libro.setCategoria(libroActualizado.getCategoria());
        libro.setIsbn(libroActualizado.getIsbn());

        return ResponseEntity.ok(libroRepository.save(libro));
    }

    // DELETE
    @DeleteMapping("/libros/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        if (!libroRepository.existsById(id)) {
            throw new EntityNotFoundException("Libro no encontrado con ID: " + id);
        }
        libroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
