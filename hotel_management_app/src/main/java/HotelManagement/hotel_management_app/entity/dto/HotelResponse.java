package HotelManagement.hotel_management_app.entity.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelResponse {
    private UUID id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;
    private Integer rating;
    private String country;
    private String city;
    private String state;
    private String zipCode;
    private String hotelType;
    
    
}
