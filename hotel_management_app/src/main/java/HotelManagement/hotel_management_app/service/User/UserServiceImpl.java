package HotelManagement.hotel_management_app.service.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HotelManagement.hotel_management_app.entity.User;
import HotelManagement.hotel_management_app.entity.UserRole;
import HotelManagement.hotel_management_app.exceptions.userExceptions.UserNotFoundException;
import HotelManagement.hotel_management_app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public User updateUser(UUID id, User user) {
        User existingUser = getUserById(id);
        
        // Actualizar campos permitidos
        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setPhone(user.getPhone());
        
        // Solo actualizar campos específicos según el rol
        if (existingUser.isEmployee()) {
            existingUser.setEmployeeCode(user.getEmployeeCode());
            existingUser.setHotel(user.getHotel());
        }
        
        if (existingUser.isGuest()) {
            existingUser.setDocumentType(user.getDocumentType());
            existingUser.setDocumentNumber(user.getDocumentNumber());
            existingUser.setNationality(user.getNationality());
            existingUser.setBirthDate(user.getBirthDate());
        }
        
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> getEmployeesByHotel(UUID hotelId) {
        return userRepository.findByHotelIdAndRoleNot(hotelId, UserRole.GUEST);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public User getUserByEmployeeCode(String employeeCode) {
        return userRepository.findByEmployeeCode(employeeCode)
            .orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public List<User> searchUsers(String email, String name, String surname, 
                                 UserRole role, String employeeCode, String documentNumber) {
        // Implementación básica - se puede mejorar con Specifications
        List<User> users = userRepository.findAll();
        
        return users.stream()
            .filter(user -> email == null || user.getEmail().toLowerCase().contains(email.toLowerCase()))
            .filter(user -> name == null || user.getName().toLowerCase().contains(name.toLowerCase()))
            .filter(user -> surname == null || user.getSurname().toLowerCase().contains(surname.toLowerCase()))
            .filter(user -> role == null || user.getRole().equals(role))
            .filter(user -> employeeCode == null || 
                   (user.getEmployeeCode() != null && user.getEmployeeCode().contains(employeeCode)))
            .filter(user -> documentNumber == null || 
                   (user.getDocumentNumber() != null && user.getDocumentNumber().contains(documentNumber)))
            .toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByEmployeeCode(String employeeCode) {
        return userRepository.existsByEmployeeCode(employeeCode);
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return userRepository.existsByDocumentNumber(documentNumber);
    }
}
