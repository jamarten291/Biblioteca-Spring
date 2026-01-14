package ogs.spring.bibliotecaspring.security.init;

import java.util.Set;

import ogs.spring.bibliotecaspring.security.model.Role;
import ogs.spring.bibliotecaspring.security.model.Usuario;
import ogs.spring.bibliotecaspring.security.repo.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class SecurityDataLoader implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    public SecurityDataLoader(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void run(String... args) {
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario(
                    "admin",
                    passwordEncoder.encode("admin123"),
                    Set.of(Role.ROLE_ADMIN, Role.ROLE_USER)             );
            usuarioRepository.save(admin);
        }
        if (!usuarioRepository.existsByUsername("user")) {
            Usuario user = new Usuario(
                    "user",
                    passwordEncoder.encode("user123"),
                    Set.of(Role.ROLE_USER)             );
            usuarioRepository.save(user);
        }
    }
}
