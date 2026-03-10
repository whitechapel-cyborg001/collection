// ═══════════════════════════════════════════════════════════════
// AuthResponse.java — DTO de salida
// Es lo que el servidor devuelve al cliente tras un login
// o registro exitoso: el token JWT que usará en futuras peticiones.
//
// SIEMPRE IGUAL: En el 99% de proyectos JWT este archivo
//    no cambia. El servidor siempre devuelve un token.
// MODIFICAR: Solo si quieres devolver más datos junto al token,
//    por ejemplo el username, el rol, o datos del perfil.
// ═══════════════════════════════════════════════════════════════

package net.vys.collection.security;  // ✏️ MODIFICAR: tu paquete

public class AuthResponse {

    // el token es siempre el dato principal de respuesta
    private String token;

    // MODIFICAR (opcional): si quieres devolver más datos al cliente
    // puedes añadir campos aquí. Ejemplos:
    //   private String username;
    //   private String role;

    // ─────────────────────────────────────────────────────────────
    // Constructor con el token — lo usará AuthController
    // para construir la respuesta antes de enviarla
    // MODIFICAR: añade parámetros si añades más campos arriba
    // ─────────────────────────────────────────────────────────────
    public AuthResponse(String token) {
        this.token = token;
    }

    // ─────────────────────────────────────────────────────────────
    // Getter — Jackson lo necesita para serializar el objeto
    // a JSON antes de enviarlo al cliente
    // El cliente recibirá: { "token": "eyJ..." }
    // ─────────────────────────────────────────────────────────────
    public String getToken() { return token; }
}