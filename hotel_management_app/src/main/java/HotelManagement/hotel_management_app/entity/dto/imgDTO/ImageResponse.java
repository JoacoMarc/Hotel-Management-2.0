package HotelManagement.hotel_management_app.entity.dto.imgDTO;

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
public class ImageResponse {
    private UUID id;
    private String imageName;
    private String imageType;
    private String imageUrl; // URL para acceder a la imagen
    private Boolean isPrimary;
    
    // Campo para datos base64 (opcional, solo cuando se necesita)
    private String file; // Campo legacy para compatibilidad
    private String imageData; // Campo nuevo más semántico
}
