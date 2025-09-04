package HotelManagement.hotel_management_app.entity.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class BookingRequest {
    private UUID id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalPrice;
    private String status;
    private List<UUID> userIds; // IDs de los usuarios que hacen la reserva 
    private UUID hotelId;
    private List<UUID> roomIds;
}
