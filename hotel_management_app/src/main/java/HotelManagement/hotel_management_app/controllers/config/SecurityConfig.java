package HotelManagement.hotel_management_app.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

import HotelManagement.hotel_management_app.entity.UserRole;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            try {
                ServletWebRequest webRequest = new ServletWebRequest(request);
                var errorResponse = globalExceptionHandler.handleAuthenticationException(authException, webRequest);
                response.setStatus(401);
                response.setContentType("application/json");
                
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                
                response.getWriter().write(mapper.writeValueAsString(errorResponse.getBody()));
            } catch (Exception e) {
                response.setStatus(401);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Authentication failed\", \"details\":\"" + e.getMessage() + "\"}");
            }
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            try {
                ServletWebRequest webRequest = new ServletWebRequest(request);
                var errorResponse = globalExceptionHandler.handleAccessDeniedException(accessDeniedException, webRequest);
                response.setStatus(403);
                response.setContentType("application/json");
                
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
                mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                
                response.getWriter().write(mapper.writeValueAsString(errorResponse.getBody()));
            } catch (Exception e) {
                response.setStatus(403);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Access denied\", \"details\":\"" + e.getMessage() + "\"}");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req -> req
                // Auth endpoints - Públicos excepto registro de empleados
                .requestMatchers("/api/v1/auth/register/guest").permitAll()
                .requestMatchers("/api/v1/auth/authenticate").permitAll()
                .requestMatchers("/api/v1/auth/register/admin").permitAll()
                .requestMatchers("/api/v1/auth/register/employee").hasAnyAuthority(UserRole.ADMIN.name(), UserRole.HOTEL_MANAGER.name())
                

                // Gestión de hoteles
                //Todos pueden ver hoteles
                .requestMatchers("GET", "/api/v1/hotels/**").permitAll()
                //Solo ADMIN puede crear, actualizar y eliminar hoteles
                .requestMatchers("POST", "/api/v1/hotels").hasAuthority(UserRole.ADMIN.name())
                .requestMatchers("PUT", "/api/v1/hotels/**").hasAuthority(UserRole.ADMIN.name())
                .requestMatchers("DELETE", "/api/v1/hotels/**").hasAuthority(UserRole.ADMIN.name())

                

                // Gestión de habitaciones
                //Todos pueden ver habitaciones
                .requestMatchers("GET", "/api/v1/rooms/**").permitAll()
                //Solo ADMIN y HOTEL_MANAGER pueden crear, actualizar y eliminar habitaciones
                .requestMatchers("POST", "/api/v1/rooms").hasAnyAuthority(UserRole.ADMIN.name(), UserRole.HOTEL_MANAGER.name())
                .requestMatchers("PUT", "/api/v1/rooms/**").hasAnyAuthority(UserRole.ADMIN.name(), UserRole.HOTEL_MANAGER.name())
                .requestMatchers("DELETE", "/api/v1/rooms/**").hasAnyAuthority(UserRole.ADMIN.name(), UserRole.HOTEL_MANAGER.name())
                
                

                // Gestión de usuarios 
                // Endpoints generales - cualquier usuario autenticado puede acceder
                .requestMatchers("GET", "/api/v1/users/**").authenticated()
                .requestMatchers("PUT", "/api/v1/users/**").authenticated()
                .requestMatchers("DELETE", "/api/v1/users/**").authenticated()
                // Endpoints específicos para empleados
                .requestMatchers("PUT", "/api/v1/users/employees/**").hasAnyAuthority(UserRole.ADMIN.name(), UserRole.HOTEL_MANAGER.name())
                .requestMatchers("GET", "/api/v1/users/employees/**").hasAnyAuthority(UserRole.ADMIN.name(), UserRole.HOTEL_MANAGER.name())
                .requestMatchers("GET", "/api/v1/users/guests/**").hasAuthority(UserRole.ADMIN.name())
                

                // Reservas
                //Todos pueden hacer reservas
                .requestMatchers("POST", "/api/v1/bookings").authenticated()
                //Employees pueden ver reservas de su hotel
                .requestMatchers("GET", "/api/v1/bookings/user/**").hasAnyAuthority(UserRole.ADMIN.name(), UserRole.HOTEL_MANAGER.name(), UserRole.FRONT_DESK_MANAGER.name(), UserRole.RECEPTIONIST.name(), UserRole.HOUSEKEEPING_MANAGER.name(), UserRole.HOUSEKEEPER.name())
                //Employees pueden actualizar reservas de su hotel
                .requestMatchers("PUT", "/api/v1/bookings/**").hasAnyAuthority(UserRole.ADMIN.name(), UserRole.HOTEL_MANAGER.name(), UserRole.FRONT_DESK_MANAGER.name(), UserRole.RECEPTIONIST.name(), UserRole.HOUSEKEEPING_MANAGER.name(), UserRole.HOUSEKEEPER.name())
                //Employees pueden eliminar reservas de su hotel
                .requestMatchers("DELETE", "/api/v1/bookings/**").hasAnyAuthority(UserRole.ADMIN.name(), UserRole.HOTEL_MANAGER.name(), UserRole.FRONT_DESK_MANAGER.name(), UserRole.RECEPTIONIST.name(), UserRole.HOUSEKEEPING_MANAGER.name(), UserRole.HOUSEKEEPER.name())

            

                

                .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler()));

        return http.build();
    }
}
