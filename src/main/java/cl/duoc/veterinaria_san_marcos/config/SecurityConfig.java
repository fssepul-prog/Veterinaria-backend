package cl.duoc.veterinaria_san_marcos.config;

import cl.duoc.veterinaria_san_marcos.security.JwtAuthenticationFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.*;
import org.springframework.web.cors.*;

@Configuration
public class SecurityConfig {
  @Bean SecurityFilterChain filterChain(HttpSecurity http,
      JwtAuthenticationFilter jwtFilter) throws Exception {
    return http.csrf(csrf -> csrf.disable())
      .cors(Customizer.withDefaults())
      .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
        // Sin esta regla, un 403 se reenvía a /error y termina como 401
        .requestMatchers("/error").permitAll()
        // Servicios: todos los autenticados leen; solo ADMIN modifica
        .requestMatchers(HttpMethod.GET, "/api/v1/servicios/**").authenticated()
        .requestMatchers("/api/v1/servicios/**").hasRole("ADMIN")
        // Citas: el dueño solicita y ve las suyas; el personal las gestiona
        .requestMatchers(HttpMethod.GET, "/api/v1/citas/mias").hasRole("CLIENTE")
        .requestMatchers(HttpMethod.POST, "/api/v1/citas").hasRole("CLIENTE")
        .requestMatchers("/api/v1/citas/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
        // Fichas y pacientes: el dueño solo ve el historial de sus mascotas
        .requestMatchers(HttpMethod.GET, "/api/v1/fichas/mias").hasRole("CLIENTE")
        .requestMatchers("/api/v1/fichas/**", "/api/v1/pacientes/**").hasAnyRole("ADMIN", "RECEPCIONISTA")
        .requestMatchers("/api/v1/reportes/**").hasRole("ADMIN")
        .anyRequest().denyAll())
      .exceptionHandling(e -> e.authenticationEntryPoint(
        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
      .build();
  }

  @Bean CorsConfigurationSource corsConfigurationSource(
      @Value("${app.cors.allowed-origin}") String allowedOrigin) {
    CorsConfiguration c = new CorsConfiguration();
    c.setAllowedOrigins(List.of(allowedOrigin));
    c.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    c.setAllowedHeaders(List.of("Content-Type", "Authorization"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/api/**", c);
    return source;
  }
}