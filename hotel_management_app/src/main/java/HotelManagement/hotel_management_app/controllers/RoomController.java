package HotelManagement.hotel_management_app.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import HotelManagement.hotel_management_app.entity.Room;
import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.dto.RoomRequest;
import HotelManagement.hotel_management_app.service.Room.RoomService;
import HotelManagement.hotel_management_app.service.Hotel.HotelService;

@RestController
public class RoomController {
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private HotelService hotelService;

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
    public List<Room> getRoomsByHotel(@PathVariable UUID hotelId) {
        return roomService.getRoomsByHotelId(hotelId);
    }
    
    @GetMapping("/api/v1/hotels/{hotelId}/rooms/{roomId}")
    public Room getRoomInHotel(@PathVariable UUID hotelId, @PathVariable UUID roomId) {
        Room room = roomService.getRoomById(roomId);
        // Validar que la habitación pertenece al hotel y/o hotel no existe
        if (!room.getHotel().getId().equals(hotelId) || room.getHotel() == null) {
            throw new RuntimeException("Room does not belong to this hotel or hotel does not exist");
        }
        return room;
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
            throw new RuntimeException("Room does not belong to this hotel");
        }
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    // ===== RUTAS GLOBALES (Para consultas generales) =====
    
    @GetMapping("/api/v1/rooms")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }
    
    @GetMapping("/api/v1/rooms/{roomId}")
    public Room getRoomById(@PathVariable UUID roomId) {
        return roomService.getRoomById(roomId);
    }

    // Búsqueda con filtros múltiples usando query parameters
    @GetMapping("/api/v1/rooms/search")
    public List<Room> searchRooms(
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
        return roomService.searchRooms(type, price, capacity, available, number, hotelId, minPrice, maxPrice, hotelName, hotelCity, hotelCountry);
    }
}