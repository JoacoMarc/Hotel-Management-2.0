package HotelManagement.hotel_management_app.entity;
import java.util.UUID;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;
    
    @Column(name = "room_type", nullable = false)
    private String roomType;
    
    @Column(name = "room_price", nullable = false)
    private double roomPrice;

    @Column(name = "room_capacity", nullable = false)
    private int roomCapacity;

    @Column(name = "room_availability", nullable = false)
    private boolean roomAvailability;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonIgnore  // No incluir en respuestas JSON
    private Hotel hotel;

    // Relación con imágenes
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Evitar bucle infinito en JSON
    private List<Image> images;
    
}
