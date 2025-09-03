package HotelManagement.hotel_management_app.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "document_type")
    private String documentType; // DNI, Pasaporte, etc.
    
    @Column(name = "document_number")
    private String documentNumber;
    
    @Column(name = "nationality")
    private String nationality;
    
    @Column(name = "birth_date")
    private LocalDate birthDate;
    
    // Relación bidireccional con reservas
    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;
}
