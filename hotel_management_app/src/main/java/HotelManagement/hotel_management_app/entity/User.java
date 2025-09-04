package HotelManagement.hotel_management_app.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "surname", nullable = false)
    private String surname;
    
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "phone")
    private String phone;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;
    
    // Campos específicos para empleados
    @Column(name = "employee_code")
    private String employeeCode;
    
    @Column(name = "hire_date")
    private LocalDate hireDate;
    
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel; // null para ADMIN y GUEST
    
    // Campos específicos para huéspedes
    @Column(name = "document_type")
    private String documentType;
    
    @Column(name = "document_number")
    private String documentNumber;
    
    @Column(name = "nationality")
    private String nationality;
    
    @Column(name = "birth_date")
    private LocalDate birthDate;
    
    // UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    
    @Override
    public String getUsername() {
        return email;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    // Helper methods
    public boolean isEmployee() {
        return role != UserRole.GUEST;
    }
    
    public boolean isGuest() {
        return role == UserRole.GUEST;
    }
    
    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }
}

