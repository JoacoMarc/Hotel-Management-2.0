package HotelManagement.hotel_management_app.entity;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "check_in_date" , nullable = false)
    private LocalDate checkInDate;
    
    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status;

    // Relación con Usuarios (huéspedes que hacen la reserva)
    @ManyToMany
    @JoinTable(
        name = "booking_users",
        joinColumns = @JoinColumn(name = "booking_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> guests;

    // Relación directa con Hotel
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    // Relación con habitaciones (del mismo hotel)
    @ManyToMany
    @JoinTable(
        name = "booking_rooms", 
        joinColumns = @JoinColumn(name = "booking_id"), 
        inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private List<Room> rooms;
}
