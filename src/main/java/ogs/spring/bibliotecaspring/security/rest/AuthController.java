package ogs.spring.bibliotecaspring.security.rest;

import jakarta.validation.Valid;
import ogs.spring.bibliotecaspring.security.jwt.JwtUtils;
import ogs.spring.bibliotecaspring.security.rest.dto.AuthResponse;
import ogs.spring.bibliotecaspring.security.rest.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())        );
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
