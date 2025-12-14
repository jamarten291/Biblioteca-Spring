package ogs.spring.bibliotecaspring.service;

import jakarta.persistence.EntityNotFoundException;
import ogs.spring.bibliotecaspring.entity.Socio;
import ogs.spring.bibliotecaspring.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocioService {
    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    public List<Socio> listarSocios() {
        return socioRepository.findAll();
    }

    public Socio guardar(Socio socio) {
        return socioRepository.save(socio);
    }

    public void eliminar(Long id) {
        Socio s = socioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Socio no encontrado con id: " + id));
        socioRepository.deleteById(id);
    }

    public List<Socio> findByNombre(String keyword) {
        return socioRepository.findByNombreContaining(keyword);
    }

    public List<Socio> findByRangoEdad(int after, int before) {
        List<Socio> resultado = new ArrayList<>();

        for (Socio s : socioRepository.findAll()) {
            if (s.getEdad() > after && s.getEdad() < before) {
                resultado.add(s);
            }
        }

        return resultado;
    }
}
