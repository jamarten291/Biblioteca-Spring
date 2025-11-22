package ogs.spring.bibliotecaspring.repository;

import ogs.spring.bibliotecaspring.entity.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SocioRepository extends JpaRepository<Socio, Long> {
}
