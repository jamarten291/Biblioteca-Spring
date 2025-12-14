package ogs.spring.bibliotecaspring.service;

import jakarta.persistence.EntityNotFoundException;
import ogs.spring.bibliotecaspring.entity.Libro;
import ogs.spring.bibliotecaspring.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {
    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public void eliminarLibro(Long id) {
        Libro l = libroRepository.findById(id)
                    .orElseThrow(() ->
                            new EntityNotFoundException("Libro no encontrado con id: " + id)
                    );
        libroRepository.deleteById(id);
    }
}
