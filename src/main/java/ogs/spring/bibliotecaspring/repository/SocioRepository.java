package ogs.spring.bibliotecaspring.repository;

import ogs.spring.bibliotecaspring.entity.Prestamo;
import ogs.spring.bibliotecaspring.entity.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
    long countPrestamosActivos(Long socioId);
}
