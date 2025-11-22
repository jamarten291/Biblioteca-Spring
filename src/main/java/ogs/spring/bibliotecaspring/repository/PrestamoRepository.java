package ogs.spring.bibliotecaspring.repository;

import ogs.spring.bibliotecaspring.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
}
