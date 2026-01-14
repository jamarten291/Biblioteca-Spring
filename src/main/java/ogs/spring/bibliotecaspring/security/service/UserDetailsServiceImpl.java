package ogs.spring.bibliotecaspring.security.service;

import java.util.stream.Collectors;

import ogs.spring.bibliotecaspring.security.model.Usuario;
import ogs.spring.bibliotecaspring.security.repo.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        return User.builder()
                .username(u.getUsername())
                .password(u.getPasswordHash())
                .disabled(!u.isEnabled())
                .authorities(
                        u.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.name()))
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
