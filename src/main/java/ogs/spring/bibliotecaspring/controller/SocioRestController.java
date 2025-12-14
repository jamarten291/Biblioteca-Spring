package ogs.spring.bibliotecaspring.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import ogs.spring.bibliotecaspring.entity.Socio;
import ogs.spring.bibliotecaspring.repository.SocioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SocioRestController {

    private final SocioRepository socioRepository;

    public SocioRestController(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    // CREATE
    @PostMapping("/socios")
    public ResponseEntity<Socio> crearSocio(@Valid @RequestBody Socio socio) {
        Socio creado = socioRepository.save(socio);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // READ ALL
    @GetMapping("/socios")
    public ResponseEntity<List<Socio>> listarSocios() {
        return ResponseEntity.ok(socioRepository.findAll());
    }

    // READ ONE
    @GetMapping("/socios/{id}")
    public ResponseEntity<Socio> obtenerSocio(@PathVariable Long id) {
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Socio no encontrado con ID: " + id));
        return ResponseEntity.ok(socio);
    }

    // UPDATE
    @PutMapping("/socios/{id}")
    public ResponseEntity<Socio> actualizarSocio(
            @PathVariable Long id,
            @Valid @RequestBody Socio socioActualizado) {

        Socio socio = socioRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Socio no encontrado con ID: " + id));

        socio.setNombre(socioActualizado.getNombre());
        socio.setApellidos(socioActualizado.getApellidos());
        socio.setEmail(socioActualizado.getEmail());
        socio.setFechaNacimiento(socioActualizado.getFechaNacimiento());

        return ResponseEntity.ok(socioRepository.save(socio));
    }

    // DELETE
    @DeleteMapping("/socios/{id}")
    public ResponseEntity<Void> eliminarSocio(@PathVariable Long id) {
        if (!socioRepository.existsById(id)) {
            throw new EntityNotFoundException("Socio no encontrado con ID: " + id);
        }
        socioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
