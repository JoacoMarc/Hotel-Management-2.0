package HotelManagement.hotel_management_app.service.Img;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import HotelManagement.hotel_management_app.entity.Image;
import HotelManagement.hotel_management_app.entity.dto.ImageRequest;
import HotelManagement.hotel_management_app.entity.dto.ImageResponse;

import java.util.List;
import java.util.UUID;

@Service
public interface ImageService {
    // Métodos básicos
    Image create(Image image);
    Image viewById(UUID id);
    List<Image> getAllImages();
    void deleteById(UUID id);

    // Métodos para hoteles
    Image uploadImageForHotel(UUID hotelId, MultipartFile file, String imageName, Boolean isPrimary);
    List<ImageRequest> getImagesByHotelId(UUID hotelId);
    ImageRequest getPrimaryImageByHotelId(UUID hotelId);
    void deleteImagesByHotelId(UUID hotelId);

    // Métodos para habitaciones
    Image uploadImageForRoom(UUID roomId, MultipartFile file, String imageName, Boolean isPrimary);
    List<ImageRequest> getImagesByRoomId(UUID roomId);
    ImageRequest getPrimaryImageByRoomId(UUID roomId);
    void deleteImagesByRoomId(UUID roomId);

    // Métodos de conversión y utilidad
    ImageRequest convertToDto(Image image);
    ImageResponse convertToResponse(Image image);
    ImageResponse getImageWithBase64(UUID id);
    byte[] getImageData(UUID imageId);
    void setPrimaryImage(UUID imageId, UUID hotelId, UUID roomId);
}
