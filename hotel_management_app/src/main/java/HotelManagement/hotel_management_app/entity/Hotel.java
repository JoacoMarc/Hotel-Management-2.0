package HotelManagement.hotel_management_app.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "address", nullable = false)
    private String address;
    
    @Column(name = "phone", nullable = false)
    private String phone;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "website", nullable = false)
    private String website;
    
    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "Country", nullable = false)
    private String country;
    
    @Column(name = "City", nullable = false)
    private String city;
    
    @Column(name = "State", nullable = false)
    private String state;
    
    @Column(name = "Zip Code", nullable = false)
    private String zipCode;
    
    @Column(name = "Hotel Type")
    private String hotelType;

    // Relaciones bidireccionales
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JsonIgnore // Evitar bucle infinito en JSON
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore // Evitar bucle infinito en JSON
    private List<Booking> bookings;

    @OneToMany(mappedBy = "hotel", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore // Evitar bucle infinito en JSON
    private List<Employee> employees;

}

