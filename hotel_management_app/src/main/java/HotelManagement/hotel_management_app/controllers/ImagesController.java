package HotelManagement.hotel_management_app.controllers;

import HotelManagement.hotel_management_app.entity.Image;
import HotelManagement.hotel_management_app.entity.dto.imgDTO.ImageResponse;
import HotelManagement.hotel_management_app.service.imgService.ImageService;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

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

    // === ENDPOINTS GENERALES PARA IMÁGENES ===
    
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

    // Obtener todas las imágenes (admin endpoint)
    @GetMapping
    public ResponseEntity<List<ImageResponse>> getAllImages() {
        List<Image> images = imageService.getAllImages();
        List<ImageResponse> imageResponses = images.stream()
                .map(imageService::convertToResponse)
                .toList();
        return ResponseEntity.ok(imageResponses);
    }
}
