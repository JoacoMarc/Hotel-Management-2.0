package HotelManagement.hotel_management_app.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "document_type", nullable = false)
    private String documentType; // DNI, Pasaporte, etc.
    
    @Column(name = "document_number", unique = true, nullable = false)
    private String documentNumber;
    
    @Column(name = "nationality", nullable = false)
    private String nationality;
    
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    
    // Relación bidireccional con reservas (Many-to-Many)
    @ManyToMany(mappedBy = "guests", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Evitar bucle infinito en JSON
    private List<Booking> bookings;
}
