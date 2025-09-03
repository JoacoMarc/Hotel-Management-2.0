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

    @Column(name = "check_in_date")
    private LocalDate checkInDate;
    
    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(name = "total_price")
    private double totalPrice;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookingStatus status;

    // Relación con Huésped
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

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
