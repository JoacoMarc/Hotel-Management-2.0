package HotelManagement.hotel_management_app.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.Image;
import HotelManagement.hotel_management_app.entity.dto.hotelDTO.HotelRequest;
import HotelManagement.hotel_management_app.entity.dto.hotelDTO.HotelResponse;
import HotelManagement.hotel_management_app.entity.dto.imgDTO.ImageResponse;
import HotelManagement.hotel_management_app.service.hotel.HotelService;
import HotelManagement.hotel_management_app.service.hotel.HotelMapper;
import HotelManagement.hotel_management_app.service.Img.ImageService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private ImageService imageService;
    
    @Autowired
    private HotelMapper hotelMapper;

    @GetMapping
    public List<HotelResponse> getAllHotels() {
        return hotelService.getAllHotelsWithImages();
    }

    
    @GetMapping("/{hotelId}")
    public HotelResponse getHotelById(@PathVariable UUID hotelId) {
        return hotelService.getHotelByIdWithImages(hotelId);
    }

    @PostMapping
    public HotelResponse createHotel(@Valid @RequestBody HotelRequest hotelRequest) {
        Hotel hotel = hotelMapper.toEntity(hotelRequest);
        Hotel createdHotel = hotelService.createHotel(hotel);
        return hotelMapper.toResponse(createdHotel);
    }

    @PutMapping("/{hotelId}")
    public HotelResponse updateHotel(@PathVariable UUID hotelId, @Valid @RequestBody HotelRequest hotelRequest) {
        Hotel existingHotel = hotelService.getHotelById(hotelId);
        hotelMapper.updateEntity(existingHotel, hotelRequest);
        Hotel updatedHotel = hotelService.updateHotel(hotelId, existingHotel);
        return hotelMapper.toResponse(updatedHotel);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable UUID hotelId) {
        hotelService.deleteHotel(hotelId);
        return ResponseEntity.noContent().build();
    }

    // Búsqueda de hoteles con filtros múltiples
    @GetMapping("/search")
    public List<HotelResponse> searchHotels(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating,
            @RequestParam(required = false) String zipCode,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email) {
        return hotelService.searchHotelsWithImages(country, city, state, type, name, minRating, maxRating, zipCode, phone, email);
    }

    // Métodos para manejo de imágenes de hoteles
    @GetMapping("/{hotelId}/images")
    public ResponseEntity<List<ImageResponse>> getHotelImages(@PathVariable UUID hotelId) {
        List<ImageResponse> images = imageService.getImageResponsesByHotelId(hotelId);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{hotelId}/images/primary")
    public ResponseEntity<ImageResponse> getHotelPrimaryImage(@PathVariable UUID hotelId) {
        ImageResponse primaryImage = imageService.getPrimaryImageResponseByHotelId(hotelId);
        if (primaryImage != null) {
            return ResponseEntity.ok(primaryImage);
        }
        return ResponseEntity.notFound().build();
    }


    
    
    // Subir imagen para un hotel
    @PostMapping("/{hotelId}/images")
    public ResponseEntity<ImageResponse> uploadImageForHotel(
            @PathVariable UUID hotelId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "imageName", required = false) String imageName,
            @RequestParam(value = "isPrimary", defaultValue = "false") Boolean isPrimary) {
        try {
            Image savedImage = imageService.uploadImageForHotel(hotelId, file, imageName, isPrimary);
            ImageResponse imageDto = imageService.convertToResponse(savedImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(imageDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}