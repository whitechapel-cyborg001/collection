package net.vys.collection.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.core.userdetails.UserDetailsService;


@Configuration          // Le dice a Spring que esta clase contiene configuración
@EnableWebSecurity      // Activa el módulo de Spring Security
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    // Inyectamos el filtro que ya creamos — Spring lo reconoce porque tiene @Component

    /*@Autowired
    private UserDetailsService userDetailsService;*/
    // Spring usará esto para cargar usuarios de la BD cuando valide credenciales

    // ─────────────────────────────────────────────────────────────
    // BEAN 1: La cadena de seguridad — el corazón de esta clase
    // Aquí defines las reglas de qué está permitido y qué no
    // ─────────────────────────────────────────────────────────────
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Desactivamos CSRF (Cross-Site Request Forgery)
            // En APIs REST con JWT no se usa — CSRF es para formularios con sesión
            .csrf(csrf -> csrf.disable())

            // Definimos qué rutas requieren autenticación y cuáles no
            .authorizeHttpRequests(auth -> auth
                // Estas rutas son públicas — cualquiera puede llamarlas sin token
                // Son las de registro y login, donde precisamente se obtiene el token
                .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                // Cualquier otra ruta requiere estar autenticado (tener token válido)
                .anyRequest().authenticated()
            )

            // Configuramos la gestión de sesión como STATELESS
            // Esto significa: Spring no guarda sesión en el servidor entre peticiones
            // Cada petición es independiente y se autentica solo con el JWT
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Aquí es donde enchufamos nuestro JwtFilter en la cadena
            // Le decimos que se ejecute ANTES del filtro estándar de usuario/contraseña
            // Así, cuando llegue una petición, primero miramos el token JWT
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // ─────────────────────────────────────────────────────────────
    // BEAN 2: El codificador de contraseñas
    // Spring Security nunca guarda contraseñas en texto plano
    // BCrypt las convierte en un hash irreversible antes de guardarlas
    // ─────────────────────────────────────────────────────────────
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ─────────────────────────────────────────────────────────────
    // BEAN 3: El AuthenticationManager
    // Es el componente que Spring usa internamente para verificar
    // que un usuario + contraseña son correctos durante el login
    // Lo necesitarás en el AuthService cuando implementes el login
    // ─────────────────────────────────────────────────────────────
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

/* El flujo completo con todo conectado

Ahora que tienes las tres piezas, así es como trabajan juntas en cada petición:
```
Petición HTTP
     │
     ▼
 JwtFilter          → Extrae y valida el token JWT
     │
     ▼
 SecurityConfig     → Comprueba si la ruta requiere autenticación
     │
     ▼
 UserDetailsService → Carga el usuario de la base de datos
     │
     ▼
 Tu Controlador     → Recibe la petición ya autenticada
  */