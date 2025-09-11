package HotelManagement.hotel_management_app.entity.dto.imgDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.*;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageRequest {
    
    private UUID id;
    
    @NotBlank(message = "El nombre de la imagen es obligatorio")
    @Size(min = 1, max = 100, message = "El nombre debe tener entre 1 y 100 caracteres")
    private String imageName;
    
    @Pattern(regexp = "image\\/(jpeg|jpg|png|gif|webp)", 
             message = "Tipo de imagen válido: jpeg, jpg, png, gif, webp")
    private String imageType;
    
    @Size(max = 500, message = "La URL no puede exceder 500 caracteres")
    private String imageUrl;
    
    // Uno de estos dos debe estar presente
    private UUID hotelId;
    private UUID roomId;
    
    @NotNull(message = "Debe especificar si la imagen es principal")
    private Boolean isPrimary;
    
    @Size(max = 10485760, message = "La imagen no puede exceder 10MB") // 10MB en bytes
    private byte[] imageData; // Para transferir los datos de la imagen (opcional)
}
