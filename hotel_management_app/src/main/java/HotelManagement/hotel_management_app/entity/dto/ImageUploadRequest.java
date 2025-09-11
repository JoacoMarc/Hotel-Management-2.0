package HotelManagement.hotel_management_app.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadRequest {
    private String imageName;
    private String imageType;
    private Boolean isPrimary;
}
