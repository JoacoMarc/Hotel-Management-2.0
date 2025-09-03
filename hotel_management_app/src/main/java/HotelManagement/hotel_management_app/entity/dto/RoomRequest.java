package HotelManagement.hotel_management_app.entity.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class RoomRequest {
    private UUID id;
    private String roomNumber;
    private String roomType;
    private double roomPrice;
    private int roomCapacity;
    private boolean roomAvailability;
    private UUID hotelId;
}
