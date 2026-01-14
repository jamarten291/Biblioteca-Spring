package ogs.spring.bibliotecaspring.security.repo;

import java.util.Optional;

import ogs.spring.bibliotecaspring.security.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
}
