package HotelManagement.hotel_management_app.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req -> req
                // Auth endpoints - Públicos excepto registro de empleados
                .requestMatchers("/api/v1/auth/register/guest").permitAll()
                .requestMatchers("/api/v1/auth/authenticate").permitAll()
                .requestMatchers("/api/v1/auth/register/admin").permitAll()
                .requestMatchers("/api/v1/auth/register/employee").hasAnyAuthority("ADMIN", "HOTEL_MANAGER")

                // Gestión de hoteles - Solo ADMIN puede crear hoteles
                .requestMatchers("POST", "/api/v1/hotels").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/hotels/**").hasAnyAuthority("ADMIN", "HOTEL_MANAGER")

                // Gestión de habitaciones - ADMIN y HOTEL_MANAGER
                .requestMatchers("/api/v1/rooms/**").hasAnyAuthority("ADMIN", "HOTEL_MANAGER")

                // Gestión de usuarios
                .requestMatchers("PUT", "/api/v1/users/**").hasAnyAuthority("ADMIN", "HOTEL_MANAGER", "GUEST")
                .requestMatchers("PATCH", "/api/v1/users/**").hasAnyAuthority("ADMIN", "HOTEL_MANAGER", "GUEST")
                .requestMatchers("/api/v1/users/**").hasAnyAuthority("ADMIN", "HOTEL_MANAGER")

                // Reservas - Diferentes permisos según acción
                .requestMatchers("POST", "/api/v1/bookings").hasAnyAuthority("GUEST", "RECEPTIONIST", "FRONT_DESK_MANAGER", "HOTEL_MANAGER", "ADMIN")
                .requestMatchers("GET", "/api/v1/bookings").hasAnyAuthority("RECEPTIONIST", "FRONT_DESK_MANAGER", "HOTEL_MANAGER", "ADMIN")
                .requestMatchers("GET", "/api/v1/bookings/user/**").hasAnyAuthority("GUEST", "RECEPTIONIST", "FRONT_DESK_MANAGER", "HOTEL_MANAGER", "ADMIN")
                .requestMatchers("PUT", "/api/v1/bookings/**").hasAnyAuthority("GUEST", "RECEPTIONIST", "FRONT_DESK_MANAGER", "HOTEL_MANAGER", "ADMIN")
                .requestMatchers("DELETE", "/api/v1/bookings/**").hasAnyAuthority("RECEPTIONIST", "FRONT_DESK_MANAGER", "HOTEL_MANAGER", "ADMIN")

                .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
