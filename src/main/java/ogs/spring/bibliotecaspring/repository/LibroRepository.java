package ogs.spring.bibliotecaspring.repository;

import ogs.spring.bibliotecaspring.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface LibroRepository extends JpaRepository<Libro, Long> {
}
