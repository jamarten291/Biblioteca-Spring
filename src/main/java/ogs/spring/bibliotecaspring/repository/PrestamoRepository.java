package ogs.spring.bibliotecaspring.repository;

import ogs.spring.bibliotecaspring.entity.Prestamo;
import ogs.spring.bibliotecaspring.entity.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findBySocioAndEstado(Socio socio, Prestamo.EstadoPrestamo estado);
    @Query("SELECT p FROM Prestamo p WHERE p.estado = 'ATRASADO'")
    List<Prestamo> findAtrasados();
    List<Prestamo> findByEstado(Prestamo.EstadoPrestamo estado);
    List<Prestamo> findByFechaPrestamoBetween(LocalDate after, LocalDate before);
}
