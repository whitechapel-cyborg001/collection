package net.vys.collection.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    // OncePerRequestFilter garantiza que este filtro se ejecuta
    // exactamente UNA vez por petición, nunca dos veces por accidente.

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    // UserDetailsService es la pieza que cargará tu usuario desde la BD.
    // La implementarás después. Spring la inyecta aquí automáticamente.

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // ─────────────────────────────────────────────────────────────
        // PASO 1: Buscar el token en el header de la petición
        // El cliente debe enviar: Authorization: Bearer <token>
        // ─────────────────────────────────────────────────────────────
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // No hay token. Dejamos pasar la petición sin autenticar.
            // Spring Security decidirá si la ruta es pública o no.
            filterChain.doFilter(request, response);
            return;
        }

        // ─────────────────────────────────────────────────────────────
        // PASO 2: Extraer el token limpio (sin el prefijo "Bearer ")
        // "Bearer eyJhbGci..." → "eyJhbGci..."
        // ─────────────────────────────────────────────────────────────
        String token = authHeader.substring(7);

        // ─────────────────────────────────────────────────────────────
        // PASO 3: Extraer el username del token
        // JwtUtil abre el token y lee el subject que pusimos al crearlo.
        // ─────────────────────────────────────────────────────────────
        String username = jwtUtil.extractUsername(token);

        // ─────────────────────────────────────────────────────────────
        // PASO 4: Comprobar que hay username y que Spring aún no
        // ha autenticado a nadie en esta petición.
        // Si SecurityContextHolder ya tiene algo, no tocamos nada.
        // ─────────────────────────────────────────────────────────────
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // ─────────────────────────────────────────────────────────
            // PASO 5: Cargar el usuario completo desde la base de datos.
            // Necesitamos sus roles y detalles, no solo el nombre.
            // ─────────────────────────────────────────────────────────
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // ─────────────────────────────────────────────────────────
            // PASO 6: Validar el token contra el usuario cargado.
            // Comprueba que el username coincide y que no ha expirado.
            // ─────────────────────────────────────────────────────────
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {

                // ─────────────────────────────────────────────────────
                // PASO 7: Crear el objeto de autenticación y meterlo
                // en el SecurityContext. Esto le dice a Spring:
                // "Este usuario está autenticado para esta petición."
                // ─────────────────────────────────────────────────────
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,                        // credentials: null porque ya validamos con JWT
                                userDetails.getAuthorities() // roles del usuario
                        );

                // Añadimos detalles de la petición HTTP (IP, session, etc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Metemos la autenticación en la cajita de Spring.
                // A partir de aquí, Spring sabe quién es el usuario.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // ─────────────────────────────────────────────────────────────
        // PASO 8: Pasar la petición al siguiente filtro o al controller.
        // Siempre hay que llamar a esto, o la petición se queda colgada.
        // ─────────────────────────────────────────────────────────────
        filterChain.doFilter(request, response);
    }
}