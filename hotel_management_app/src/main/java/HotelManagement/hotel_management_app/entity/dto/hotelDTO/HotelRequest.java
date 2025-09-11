package HotelManagement.hotel_management_app.entity.dto.hotelDTO;

import java.util.UUID;
import lombok.Data;

@Data
public class HotelRequest {
    private UUID id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;
    private int rating;
    private String country;
    private String city;
    private String state;
    private String zipCode;
    private String hotelType;
}
