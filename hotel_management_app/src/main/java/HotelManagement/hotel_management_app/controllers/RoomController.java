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
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import HotelManagement.hotel_management_app.entity.Room;
import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.Image;
import HotelManagement.hotel_management_app.entity.dto.RoomRequest;
import HotelManagement.hotel_management_app.entity.dto.RoomResponse;
import HotelManagement.hotel_management_app.entity.dto.ImageResponse;
import HotelManagement.hotel_management_app.exceptions.roomExceptions.RoomBelongsToDifferentHotelException;
import HotelManagement.hotel_management_app.service.Room.RoomService;
import HotelManagement.hotel_management_app.service.Hotel.HotelService;
import HotelManagement.hotel_management_app.service.Img.ImageService;

@RestController
public class RoomController {
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private HotelService hotelService;

    @Autowired
    private ImageService imageService;

    // ===== RUTAS ANIDADAS (Recomendadas para operaciones específicas de hotel) =====
    
    @PostMapping("/api/v1/hotels/{hotelId}/rooms")
    public Room createRoomInHotel(@PathVariable UUID hotelId, @RequestBody RoomRequest roomRequest) {
        // Obtener el hotel real de la base de datos
        Hotel hotel = hotelService.getHotelById(hotelId);
        
        // Crear la habitación con los datos del request
        Room room = new Room();
        room.setRoomNumber(roomRequest.getRoomNumber());
        room.setRoomType(roomRequest.getRoomType());
        room.setRoomPrice(roomRequest.getRoomPrice());
        room.setRoomCapacity(roomRequest.getRoomCapacity());
        room.setRoomAvailability(roomRequest.isRoomAvailability());
        room.setHotel(hotel);  // Asignar el hotel REAL
        
        return roomService.createRoom(room);
    }
    
    @GetMapping("/api/v1/hotels/{hotelId}/rooms")
    public List<RoomResponse> getRoomsByHotel(@PathVariable UUID hotelId) {
        return roomService.getRoomsByHotelIdWithImages(hotelId);
    }
    
    @GetMapping("/api/v1/hotels/{hotelId}/rooms/{roomId}")
    public RoomResponse getRoomInHotel(@PathVariable UUID hotelId, @PathVariable UUID roomId) {
        Room room = roomService.getRoomById(roomId);
        // Validar que la habitación pertenece al hotel y/o hotel no existe
        if (!room.getHotel().getId().equals(hotelId) || room.getHotel() == null) {
            throw new RoomBelongsToDifferentHotelException("La habitación no pertenece a este hotel o el hotel no existe");
        }
        return roomService.convertToResponseWithImages(room);
    }
    
    @PutMapping("/api/v1/hotels/{hotelId}/rooms/{roomId}")
    public Room updateRoomInHotel(@PathVariable UUID hotelId, @PathVariable UUID roomId, @RequestBody Room room) {
        // Asegurar que la habitación sigue perteneciendo al hotel correcto
        if (room.getHotel() == null) {
            room.setHotel(new HotelManagement.hotel_management_app.entity.Hotel());
        }
        room.getHotel().setId(hotelId);
        return roomService.updateRoom(roomId, room);
    }
    
    @DeleteMapping("/api/v1/hotels/{hotelId}/rooms/{roomId}")
    public ResponseEntity<Void> deleteRoomFromHotel(@PathVariable UUID hotelId, @PathVariable UUID roomId) {
        Room room = roomService.getRoomById(roomId);
        // Validar que la habitación pertenece al hotel
        if (!room.getHotel().getId().equals(hotelId)) {
            throw new RoomBelongsToDifferentHotelException("La habitación no pertenece a este hotel");
        }
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    // ===== RUTAS GLOBALES (Para consultas generales) =====
    
    @GetMapping("/api/v1/rooms")
    public List<RoomResponse> getAllRooms() {
        return roomService.getAllRoomsWithImages();
    }
    
    @GetMapping("/api/v1/rooms/{roomId}")
    public RoomResponse getRoomById(@PathVariable UUID roomId) {
        return roomService.getRoomByIdWithImages(roomId);
    }

    // Búsqueda con filtros múltiples usando query parameters
    @GetMapping("/api/v1/rooms/search")
    public List<RoomResponse> searchRooms(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) Boolean available,
            @RequestParam(required = false) String number,
            @RequestParam(required = false) UUID hotelId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String hotelName,
            @RequestParam(required = false) String hotelCity,
            @RequestParam(required = false) String hotelCountry) {
        return roomService.searchRoomsWithImages(type, price, capacity, available, number, hotelId, minPrice, maxPrice, hotelName, hotelCity, hotelCountry);
    }

    // Métodos para manejo de imágenes de habitaciones
    @GetMapping("/api/v1/rooms/{roomId}/images")
    public ResponseEntity<List<ImageResponse>> getRoomImages(@PathVariable UUID roomId) {
        List<ImageResponse> images = imageService.getImageResponsesByRoomId(roomId);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/api/v1/rooms/{roomId}/images/primary")
    public ResponseEntity<ImageResponse> getRoomPrimaryImage(@PathVariable UUID roomId) {
        ImageResponse primaryImage = imageService.getPrimaryImageResponseByRoomId(roomId);
        if (primaryImage != null) {
            return ResponseEntity.ok(primaryImage);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Subir imagen para una habitación
    @PostMapping("/api/v1/rooms/{roomId}/images")
    public ResponseEntity<ImageResponse> uploadImageForRoom(
            @PathVariable UUID roomId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "imageName", required = false) String imageName,
            @RequestParam(value = "isPrimary", defaultValue = "false") Boolean isPrimary) {
        try {
            Image savedImage = imageService.uploadImageForRoom(roomId, file, imageName, isPrimary);
            ImageResponse imageDto = imageService.convertToResponse(savedImage);
            return ResponseEntity.status(HttpStatus.CREATED).body(imageDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}