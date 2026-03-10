// ═══════════════════════════════════════════════════════════════
// AuthRequest.java — DTO de entrada
// DTO = Data Transfer Object. Es un objeto simple cuyo único
// propósito es transportar datos del cliente al servidor.
// No tiene lógica, no tiene anotaciones de base de datos.
//
// SIEMPRE IGUAL: La estructura de este archivo es estándar.
// MODIFICAR: Los campos según lo que necesite tu login.
//     En la mayoría de proyectos será username+password,
//     pero podría ser email+password, o añadir más campos.
// ═══════════════════════════════════════════════════════════════

package net.vys.collection.security;  // ✏️ MODIFICAR: tu paquete

public class AuthRequest {

    // MODIFICAR: estos son los campos que el cliente enviará en el JSON
    // Ejemplo mínimo típico: username + password
    // Si usas email en vez de username, cambia el nombre del campo
    private String username;
    private String password;

    // ─────────────────────────────────────────────────────────────
    // Constructor vacío — obligatorio para que Jackson pueda
    // deserializar el JSON del body en este objeto automáticamente
    // SIEMPRE IGUAL
    // ─────────────────────────────────────────────────────────────
    public AuthRequest() {}

    // ─────────────────────────────────────────────────────────────
    // Getters y Setters
    // Jackson los necesita para leer y escribir los campos
    // MODIFICAR: añade/quita según tus campos
    // ─────────────────────────────────────────────────────────────
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}