package HotelManagement.hotel_management_app.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomResponse {
    
    // Core Room Information
    private UUID id;
    private String roomNumber;
    private String roomType;
    private double roomPrice;
    private int roomCapacity;
    private boolean roomAvailability;
    
}
