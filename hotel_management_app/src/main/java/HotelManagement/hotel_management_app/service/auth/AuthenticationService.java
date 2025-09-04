package HotelManagement.hotel_management_app.service.auth;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import HotelManagement.hotel_management_app.controllers.auth.AuthenticationRequest;
import HotelManagement.hotel_management_app.controllers.auth.AuthenticationResponse;
import HotelManagement.hotel_management_app.controllers.auth.EmployeeRegisterRequest;
import HotelManagement.hotel_management_app.controllers.auth.GuestRegisterRequest;
import HotelManagement.hotel_management_app.controllers.config.JwtService;
import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.User;
import HotelManagement.hotel_management_app.entity.UserRole;
import HotelManagement.hotel_management_app.repository.HotelRepository;
import HotelManagement.hotel_management_app.repository.UserRepository;
import HotelManagement.hotel_management_app.exceptions.UserDuplicateException;
import HotelManagement.hotel_management_app.exceptions.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerEmployee(EmployeeRegisterRequest request) {
        // Buscar el hotel
        Hotel hotel = hotelRepository.findById(UUID.fromString(request.getHotelId()))
            .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));

        // Verificar si el email ya existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserDuplicateException("Email ya registrado");
        }

        // Verificar si el código de empleado ya existe
        if (userRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new UserDuplicateException("Código de empleado ya existe");
        }

        var user = User.builder()
            .name(request.getName())
            .surname(request.getSurname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .phone(request.getPhone())
            .role(request.getRole())
            .employeeCode(request.getEmployeeCode())
            .hireDate(LocalDate.now())
            .hotel(hotel)
            .build();
            
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .build();
    }

    public AuthenticationResponse registerGuest(GuestRegisterRequest request) {
        // Verificar si el email ya existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserDuplicateException("Email ya registrado");
        }

        // Verificar si el número de documento ya existe
        if (userRepository.existsByDocumentNumber(request.getDocumentNumber())) {
            throw new UserDuplicateException("Número de documento ya registrado");
        }

        var user = User.builder()
            .name(request.getName())
            .surname(request.getSurname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .phone(request.getPhone())
            .role(UserRole.GUEST)
            .documentType(request.getDocumentType())
            .documentNumber(request.getDocumentNumber())
            .nationality(request.getNationality())
            .birthDate(request.getBirthDate())
            .build();
            
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .build();
    }

    public AuthenticationResponse registerAdmin(EmployeeRegisterRequest request) {
        // Verificar si el email ya existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserDuplicateException("Email ya registrado");
        }

        var admin = User.builder()
            .name(request.getName())
            .surname(request.getSurname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .phone(request.getPhone())
            .role(UserRole.ADMIN)
            .employeeCode(request.getEmployeeCode())
            .hireDate(LocalDate.now())
            .hotel(null) // ADMIN no pertenece a un hotel específico
            .build();
            
        userRepository.save(admin);
        var jwtToken = jwtService.generateToken(admin);
        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        
        var user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UserNotFoundException());
        
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .build();
    }
}