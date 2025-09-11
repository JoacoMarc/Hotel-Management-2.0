package HotelManagement.hotel_management_app.controllers.img;

import HotelManagement.hotel_management_app.entity.Image;
import HotelManagement.hotel_management_app.entity.dto.AddFileRequest;
import HotelManagement.hotel_management_app.entity.dto.ImageRequest;
import HotelManagement.hotel_management_app.entity.dto.ImageResponse;
import HotelManagement.hotel_management_app.service.Img.ImageService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/images")
@CrossOrigin
public class ImagesController {
    
    @Autowired
    private ImageService imageService;

    // Obtener imagen por ID (devuelve los datos binarios)
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable UUID id) {
        try {
            Image image = imageService.viewById(id);
            byte[] imageData = imageService.getImageData(id);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(image.getImageType()));
            headers.setContentLength(imageData.length);
            
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener información de imagen por ID (metadata)
    @GetMapping("/{id}/info")
    public ResponseEntity<ImageResponse> getImageInfo(@PathVariable UUID id) {
        try {
            Image image = imageService.viewById(id);
            ImageResponse imageResponse = imageService.convertToResponse(image);
            return ResponseEntity.ok(imageResponse);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener imagen codificada en base64 (para compatibilidad)
    @GetMapping("/{id}/base64")
    public ResponseEntity<ImageResponse> getImageBase64(@PathVariable UUID id) {
        try {
            ImageResponse imageResponse = imageService.getImageWithBase64(id);
            return ResponseEntity.ok(imageResponse);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // === ENDPOINTS PARA HOTELES ===
    
    // Subir imagen para un hotel
    @PostMapping("/hotels/{hotelId}")
    public ResponseEntity<ImageRequest> uploadImageForHotel(
            @PathVariable UUID hotelId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "imageName", required = false) String imageName,
            @RequestParam(value = "isPrimary", defaultValue = "false") Boolean isPrimary) {
        try {
            Image savedImage = imageService.uploadImageForHotel(hotelId, file, imageName, isPrimary);
            ImageRequest imageDto = imageService.convertToDto(savedImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(imageDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Obtener todas las imágenes de un hotel
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<ImageRequest>> getImagesByHotelId(@PathVariable UUID hotelId) {
        List<ImageRequest> images = imageService.getImagesByHotelId(hotelId);
        return ResponseEntity.ok(images);
    }

    // Obtener la imagen principal de un hotel
    @GetMapping("/hotels/{hotelId}/primary")
    public ResponseEntity<ImageRequest> getPrimaryImageByHotelId(@PathVariable UUID hotelId) {
        ImageRequest primaryImage = imageService.getPrimaryImageByHotelId(hotelId);
        if (primaryImage != null) {
            return ResponseEntity.ok(primaryImage);
        }
        return ResponseEntity.notFound().build();
    }

    // === ENDPOINTS PARA HABITACIONES ===
    
    // Subir imagen para una habitación
    @PostMapping("/rooms/{roomId}")
    public ResponseEntity<ImageRequest> uploadImageForRoom(
            @PathVariable UUID roomId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "imageName", required = false) String imageName,
            @RequestParam(value = "isPrimary", defaultValue = "false") Boolean isPrimary) {
        try {
            Image savedImage = imageService.uploadImageForRoom(roomId, file, imageName, isPrimary);
            ImageRequest imageDto = imageService.convertToDto(savedImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(imageDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Obtener todas las imágenes de una habitación
    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<List<ImageRequest>> getImagesByRoomId(@PathVariable UUID roomId) {
        List<ImageRequest> images = imageService.getImagesByRoomId(roomId);
        return ResponseEntity.ok(images);
    }

    // Obtener la imagen principal de una habitación
    @GetMapping("/rooms/{roomId}/primary")
    public ResponseEntity<ImageRequest> getPrimaryImageByRoomId(@PathVariable UUID roomId) {
        ImageRequest primaryImage = imageService.getPrimaryImageByRoomId(roomId);
        if (primaryImage != null) {
            return ResponseEntity.ok(primaryImage);
        }
        return ResponseEntity.notFound().build();
    }

    // === ENDPOINTS GENERALES ===
    
    // Establecer imagen como principal
    @PutMapping("/{imageId}/set-primary")
    public ResponseEntity<String> setPrimaryImage(
            @PathVariable UUID imageId,
            @RequestParam(value = "hotelId", required = false) UUID hotelId,
            @RequestParam(value = "roomId", required = false) UUID roomId) {
        try {
            imageService.setPrimaryImage(imageId, hotelId, roomId);
            return ResponseEntity.ok("Image set as primary successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error setting image as primary: " + e.getMessage());
        }
    }

    // Eliminar imagen
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable UUID id) {
        try {
            imageService.deleteById(id);
            return ResponseEntity.ok("Image deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting image: " + e.getMessage());
        }
    }

    // Obtener todas las imágenes
    @GetMapping
    public ResponseEntity<List<ImageRequest>> getAllImages() {
        List<Image> images = imageService.getAllImages();
        List<ImageRequest> imageDtos = images.stream()
                .map(imageService::convertToDto)
                .toList();
        return ResponseEntity.ok(imageDtos);
    }

    // Método legacy para compatibilidad
    @PostMapping
    public String addImagePost(AddFileRequest request) throws IOException, SerialException, SQLException {
        byte[] bytes = request.getFile().getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        imageService.create(Image.builder().image(blob).build());
        return "created";
    }
}
