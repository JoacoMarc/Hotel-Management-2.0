package HotelManagement.hotel_management_app.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import HotelManagement.hotel_management_app.entity.User;
import HotelManagement.hotel_management_app.entity.UserRole;
import HotelManagement.hotel_management_app.entity.dto.userDTO.UserResponse;
import HotelManagement.hotel_management_app.service.user.UserMapper;
import HotelManagement.hotel_management_app.service.user.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserMapper userMapper;

    // Obtener todos los usuarios (solo ADMIN)
    @GetMapping
    public List<UserResponse> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    // Obtener usuario por ID
    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return userMapper.toUserResponse(user);
    }

    // Obtener empleados por hotel (HOTEL_MANAGER o ADMIN)
    @GetMapping("/employees/hotel/{hotelId}")
    public List<UserResponse> getEmployeesByHotel(@PathVariable UUID hotelId) {
        List<User> employees = userService.getEmployeesByHotel(hotelId);
        return employees.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    // Obtener huéspedes (ADMIN o empleados del hotel)
    @GetMapping("/guests")
    public List<UserResponse> getAllGuests() {
        List<User> guests = userService.getUsersByRole(UserRole.GUEST);
        return guests.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    // Actualizar usuario
    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable UUID userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
        return userMapper.toUserResponse(updatedUser);
    }

    // Eliminar usuario (solo ADMIN)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // Búsqueda de usuarios con filtros
    @GetMapping("/search")
    public List<UserResponse> searchUsers(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) UserRole role,
            @RequestParam(required = false) String employeeCode,
            @RequestParam(required = false) String documentNumber) {
        List<User> users = userService.searchUsers(email, name, surname, role, employeeCode, documentNumber);
        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }
}
