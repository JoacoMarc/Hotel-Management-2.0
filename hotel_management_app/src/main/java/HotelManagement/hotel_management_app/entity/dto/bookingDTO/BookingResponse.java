package HotelManagement.hotel_management_app.entity.dto.bookingDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import HotelManagement.hotel_management_app.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Solo incluir campos no-null
public class BookingResponse {
    
    private UUID id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;
    private BookingStatus status;
    
    // Información básica de huéspedes (SIN password ni detalles internos)
    private List<GuestInfo> guests;
    
    // Información básica del hotel
    private HotelInfo hotel;
    
    // Información de las habitaciones
    private List<RoomInfo> rooms;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuestInfo {
        private UUID id;
        private String name;
        private String surname;
        private String email;
        private String phone;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotelInfo {
        private UUID id;
        private String name;
        private String address;
        private String phone;
        private String city;
        private String country;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomInfo {
        private UUID id;
        private String roomNumber;
        private String roomType;
        private double roomPrice;
        private int roomCapacity;
    }
}

