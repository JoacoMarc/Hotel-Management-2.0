package HotelManagement.hotel_management_app.entity.dto.userDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestRegisterRequest {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private String documentType; // DNI, Pasaporte, etc.
    private String documentNumber;
    private String nationality;
    private LocalDate birthDate;
}
