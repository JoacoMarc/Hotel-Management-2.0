package HotelManagement.hotel_management_app.entity.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Data;

@Data
public class GuestRequest {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String documentType;
    private String documentNumber;
    private String nationality;
    private LocalDate birthDate;
}
