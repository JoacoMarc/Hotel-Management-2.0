package HotelManagement.hotel_management_app.entity.dto.imgDTO;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddFileRequest {
    private String name;
    private MultipartFile file;
}
