// ═══════════════════════════════════════════════════════════════
// AuthController.java — Endpoints HTTP de autenticación
// Su único trabajo es recibir la petición HTTP, pasarla al
// AuthService, y devolver la respuesta al cliente.
// No contiene lógica — eso es responsabilidad del Service.
//
// SIEMPRE IGUAL: La estructura completa de este archivo es
//    estándar en cualquier proyecto JWT con Spring Security.
//    Solo cambia el paquete y opcionalmente la ruta base.
// MODIFICAR: la ruta base si no quieres /api/auth
// ═══════════════════════════════════════════════════════════════

package net.vys.collection.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController   // marca como controlador REST (devuelve JSON)
@RequestMapping("/api/auth")  // MODIFICAR (opcional): ruta base de autenticación
                               // Esta ruta debe coincidir con la que pusiste
                               // en SecurityConfig como .requestMatchers("/api/auth/**").permitAll()
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ═══════════════════════════════════════════════════════════
    // POST /api/auth/register
    // El cliente envía: { "username": "vyserad", "password": "1234" }
    // El servidor devuelve: { "token": "eyJ..." }
    // ═══════════════════════════════════════════════════════════
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        // @RequestBody indica a Spring que deserialice el JSON del body
        // en un objeto AuthRequest automáticamente

        String token = authService.register(request);

        // ResponseEntity.ok() devuelve un HTTP 200 con el body que le pases
        return ResponseEntity.ok(new AuthResponse(token));
    }


    // ═══════════════════════════════════════════════════════════
    // POST /api/auth/login
    // El cliente envía: { "username": "vyserad", "password": "1234" }
    // El servidor devuelve: { "token": "eyJ..." }
    // Si las credenciales son incorrectas, Spring devuelve 401 automáticamente
    // ═══════════════════════════════════════════════════════════
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        String token = authService.login(request);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}