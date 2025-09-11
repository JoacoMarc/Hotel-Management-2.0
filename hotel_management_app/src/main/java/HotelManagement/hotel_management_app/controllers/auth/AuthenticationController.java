package HotelManagement.hotel_management_app.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import HotelManagement.hotel_management_app.entity.dto.userDTO.EmployeeRegisterRequest;
import HotelManagement.hotel_management_app.entity.dto.userDTO.GuestRegisterRequest;
import HotelManagement.hotel_management_app.service.auth.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register/employee")
    public ResponseEntity<AuthenticationResponse> registerEmployee(
            @Valid @RequestBody EmployeeRegisterRequest request) {
        return ResponseEntity.ok(service.registerEmployee(request));
    }

    @PostMapping("/register/guest")
    public ResponseEntity<AuthenticationResponse> registerGuest(
            @Valid @RequestBody GuestRegisterRequest request) {
        return ResponseEntity.ok(service.registerGuest(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    
}