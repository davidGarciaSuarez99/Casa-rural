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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                                // Público — sin necesidad de cuenta
                                .requestMatchers("/api/reservas/disponibilidad").permitAll()
                                .requestMatchers("/api/auth/login").permitAll()
                                .requestMatchers("/api/auth/registro").permitAll()
                                .requestMatchers("/api/alojamientos/**").permitAll()
                                .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/contacto").permitAll()


                                // Solo admin
                                .requestMatchers("/api/auth/usuarios/**").hasRole("ADMIN")
                                .requestMatchers("/api/auth/usuarios/**").hasRole("ADMIN")
                                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/contacto").hasRole("ADMIN")   // ← CAMBIAR
                                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/contacto/noleidos").hasRole("ADMIN")
                                .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/contacto/**").hasRole("ADMIN")


                                // 1. Permite que cualquiera consulte el endpoint de fechas ocupadas (petición GET)
                                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/reservas/ocupadas").permitAll()

                                // 2. Obliga a estar logueado para todo lo demás de reservas (crear, ver mis reservas particulares...)
                                .requestMatchers("/api/reservas/**").authenticated()
                                // 1. Permite que cualquiera VEA las reseñas (peticiones GET) de forma pública
                                .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/resenas/**").permitAll()
                                // 2. Obliga a iniciar sesión para el resto de acciones (crear, borrar...)
                                .requestMatchers("/api/resenas/**").authenticated()

                                // Todo lo demás autenticado
                                .anyRequest().authenticated()


			// .anyRequest().permitAll()



                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:63342",
                "http://127.0.0.1:63342",
                "http://localhost:5500",
                "http://127.0.0.1:5500"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        configuration.setExposedHeaders(List.of("Authorization"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}