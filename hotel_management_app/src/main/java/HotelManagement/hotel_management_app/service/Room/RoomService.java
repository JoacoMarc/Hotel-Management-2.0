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
    List<Room> getRoomsByRoomType(String roomType);
    List<Room> getRoomsByRoomPrice(double roomPrice);
    List<Room> getRoomsByRoomCapacity(int roomCapacity);
    List<Room> getRoomsByRoomAvailability(boolean roomAvailability);
    List<Room> getRoomsByRoomNumber(String roomNumber);
}

    
