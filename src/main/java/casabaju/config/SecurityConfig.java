package casabaju.config;

import casabaju.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    // Define qué rutas son públicas y cuáles requieren autenticación o rol de admin.
    //
    // ZONA PÚBLICA (sin login):
    //   - Ver alojamientos, comprobar disponibilidad, login, registro,
    //     enviar mensaje de contacto y ver reseñas aprobadas.
    //
    // SOLO ADMIN:
    //   - Ver todos los usuarios, mensajes de contacto y marcarlos como leídos/respondidos.
    //
    // USUARIOS LOGUEADOS:
    //   - Crear y consultar reservas, crear reseñas.
    //
    // También configura CORS para permitir peticiones desde el frontend
    // (localhost:63342 y localhost:5500, los puertos típicos de IntelliJ y Live Server).

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // 1. ZONA TOTALMENTE PÚBLICA (No requieren login)
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/reservas/ocupadas").permitAll()
                        .requestMatchers("/api/reservas/disponibilidad").permitAll()

                        // Importante: Ponemos tanto la ruta con barra como sin barra
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/resenas").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/resenas/**").permitAll()

                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/registro").permitAll()
                        .requestMatchers("/api/alojamientos/**").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/contacto").permitAll()

                        // 2. ZONA RESTRINGIDA A ADMINISTRADORES
                        .requestMatchers("/api/auth/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/contacto").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/contacto/noleidos").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/contacto/**").hasRole("ADMIN")

                        // 3. ZONA CLIENTES LOGUEADOS (Reservas y creación de reseñas)
                        .requestMatchers("/api/reservas/**").authenticated()
                        .requestMatchers("/api/resenas/**").authenticated()

                        // 4. Todo lo demás por defecto
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Orígenes permitidos: los puertos donde corre el frontend.
        // 63342 es el puerto de IntelliJ al abrir HTML directamente,
        // 5500 es el puerto de Live Server (extensión de VS Code).
        // Sin esto, el navegador bloquearía todas las peticiones al backend.
        configuration.setAllowedOrigins(List.of(
                "http://localhost:63342",
                "http://127.0.0.1:63342",
                "http://localhost:5500",
                "http://127.0.0.1:5500"
        ));

        // Métodos HTTP que se permiten desde el frontend.
        // OPTIONS es obligatorio porque el navegador lo manda primero
        // como "preflight" para comprobar si tiene permiso antes de la petición real.
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Cabeceras que el frontend puede enviar en sus peticiones.
        // Authorization es la que lleva el token JWT (Bearer xxx).
        // Content-Type indica que mandamos JSON.
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));

        // Cabeceras que el frontend puede leer de la respuesta del servidor.
        // Necesario para que el JS pueda acceder al token si el servidor lo devuelve en cabecera.
        configuration.setExposedHeaders(List.of("Authorization"));

        // Permite que las peticiones incluyan credenciales (cookies, tokens).
        // Necesario para que el token JWT funcione correctamente.
        configuration.setAllowCredentials(true);

        // Aplica esta configuración CORS a todas las rutas de la API (/**)
        // y la registra en Spring para que se use automáticamente.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}