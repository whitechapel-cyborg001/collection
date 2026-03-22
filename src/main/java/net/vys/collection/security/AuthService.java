// ═══════════════════════════════════════════════════════════════
// AuthService.java — Lógica de negocio de autenticación
// Contiene lo que ocurre realmente cuando alguien se registra
// o hace login. El Controller solo recibe la petición y delega
// aquí. Separar lógica de controladores es buena práctica.
//
// SIEMPRE IGUAL: Las inyecciones, la estructura de register()
//    y login(), y el uso de authenticationManager son estándar
//    en cualquier proyecto JWT con Spring Security.
// MODIFICAR: El nombre de la entidad (User), el repositorio,
//    los campos usados, y el rol por defecto en el registro.
// ═══════════════════════════════════════════════════════════════

package net.vys.collection.security;

import net.vys.collection.entities.User;              // MODIFICAR: ruta a tu entidad User
import net.vys.collection.exceptions.UsernameAlreadyExistsException;
import net.vys.collection.repositories.UserRepository; // MODIFICAR: ruta a tu repositorio

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service  // marca esta clase como servicio de Spring
public class AuthService {

    // ─────────────────────────────────────────────────────────────
    // Dependencias inyectadas por Spring
    // MODIFICAR: UserRepository por el repositorio de tu proyecto
    // el resto de inyecciones son estándar JWT
    // ─────────────────────────────────────────────────────────────

    @Autowired
    private UserRepository userRepository;          // MODIFICAR: tu repositorio de usuarios

    @Autowired
    private PasswordEncoder passwordEncoder;
    // BCryptPasswordEncoder — definido como Bean en SecurityConfig
    // Nunca guardes contraseñas en texto plano

    @Autowired
    private AuthenticationManager authenticationManager;
    // Definido como Bean en SecurityConfig
    // Se encarga de verificar usuario+contraseña contra la BD

    @Autowired
    private JwtUtil jwtUtil;
    // Tu clase de utilidad para generar y validar tokens JWT


    // ═══════════════════════════════════════════════════════════
    // REGISTRO — POST /api/auth/register
    // ═══════════════════════════════════════════════════════════
    public String register(AuthRequest request) {

        // Lanza excepción si el username ya existe en la BD
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }
        // MODIFICAR: ajusta los parámetros del constructor de User
        // según los campos de tu entidad.
        // Si tienes email en vez de username, cámbialo aquí.
        User user = new User(
            request.getUsername(),                          // MODIFICAR si usas email u otro campo
            passwordEncoder.encode(request.getPassword()),  // hashear la contraseña
            "USER"                                          // MODIFICAR: rol por defecto de tu app
            // Otros proyectos pueden usar "ROLE_USER", "ADMIN", etc.
        );

        // guardar el usuario en la BD
        userRepository.save(user);

        // generar y devolver el token tras el registro
        // Así el usuario queda logado automáticamente al registrarse
        return jwtUtil.generateToken(user.getUsername());  // MODIFICAR si el identificador no es username
    }


    // ═══════════════════════════════════════════════════════════
    // LOGIN — POST /api/auth/login
    // ═══════════════════════════════════════════════════════════
    public String login(AuthRequest request) {

        // delegar la verificación en Spring Security
        // authenticationManager llama internamente a:
        //   1. UserDetailsServiceImpl.loadUserByUsername()  → busca el usuario en BD
        //   2. BCrypt.matches()                             → compara contraseñas
        // Si algo falla, lanza BadCredentialsException automáticamente (401)
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),   // MODIFICAR si usas email u otro campo
                request.getPassword()
            )
        );

        // Si llegamos aquí, las credenciales son correctas
        //  generar y devolver el token
        return jwtUtil.generateToken(request.getUsername());  // MODIFICAR si el identificador no es username
    }
}