package casabaju.controller;

import casabaju.model.Usuario;
import casabaju.repository.UsuarioRepository;
import casabaju.security.JwtUtil;
import casabaju.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // POST /api/auth/login → iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email    = body.get("email");
        String password = body.get("password");

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Email o contraseña incorrectos");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.getActivo()) {
            return ResponseEntity.status(401).body("Usuario desactivado");
        }

        if (!passwordEncoder.matches(password, usuario.getPasswordHash())) {
            return ResponseEntity.status(401).body("Email o contraseña incorrectos");
        }

        // Generar token JWT
        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol().name());

        return ResponseEntity.ok(Map.of(
            "token",    token,
            "id",       usuario.getId(),
            "nombre",   usuario.getNombre(),
            "apellidos",usuario.getApellidos(),
            "email",    usuario.getEmail(),
            "rol",      usuario.getRol().name()
        ));
    }

    // POST /api/auth/registro → registrar nuevo usuario
    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.registrar(usuario);
            nuevo.setPasswordHash(null);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> obtenerUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }
}

