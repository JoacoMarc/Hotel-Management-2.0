package HotelManagement.hotel_management_app.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageRequest {
    private UUID id;
    private String imageName;
    private String imageType;
    private String imageUrl; // URL para acceder a la imagen
    private UUID hotelId;
    private UUID roomId;
    private Boolean isPrimary;
    private byte[] imageData; // Para transferir los datos de la imagen (opcional)
}
