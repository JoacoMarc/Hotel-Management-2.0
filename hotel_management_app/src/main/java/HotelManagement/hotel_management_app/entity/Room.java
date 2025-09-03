package HotelManagement.hotel_management_app.entity;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "room_number")
    private String roomNumber;
    
    @Column(name = "room_type")
    private String roomType;
    
    @Column(name = "room_price")
    private double roomPrice;

    @Column(name = "room_capacity")
    private int roomCapacity;

    @Column(name = "room_availability")
    private boolean roomAvailability;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    
}
