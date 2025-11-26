package ogs.spring.bibliotecaspring.service;

import ogs.spring.bibliotecaspring.entity.Socio;
import ogs.spring.bibliotecaspring.repository.LibroRepository;
import ogs.spring.bibliotecaspring.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class SocioService {
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
        socioRepository.deleteById(id);
    }
}
