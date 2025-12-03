package ogs.spring.bibliotecaspring.repository;

import ogs.spring.bibliotecaspring.entity.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
    @Query("SELECT count(p) FROM Socio s JOIN Prestamo p WHERE s = p.socio AND p.estado = 'ACTIVO'")
    long countPrestamosActivos(@Param("id_param") Long socioId);
}
