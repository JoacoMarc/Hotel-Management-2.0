package HotelManagement.hotel_management_app.service.Room;

import java.util.List;
import java.util.UUID;

import HotelManagement.hotel_management_app.entity.Room;

public interface RoomService {
    List<Room> getAllRooms();
    Room getRoomById(UUID id);
    Room createRoom(Room room);
    Room updateRoom(UUID id, Room room);
    void deleteRoom(UUID id);
    List<Room> getRoomsByHotelId(UUID hotelId);
    // Método de búsqueda con múltiples filtros opcionales
    List<Room> searchRooms(String type, Double price, Integer capacity, Boolean available, String number, UUID hotelId, Double minPrice, Double maxPrice, String hotelName, String hotelCity, String hotelCountry);
}

    
