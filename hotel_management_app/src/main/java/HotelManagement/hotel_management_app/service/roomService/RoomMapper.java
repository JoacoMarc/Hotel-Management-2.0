package HotelManagement.hotel_management_app.service.roomService;

import org.springframework.stereotype.Component;

import HotelManagement.hotel_management_app.entity.Room;
import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.dto.roomDTO.RoomRequest;

@Component
public class RoomMapper {
    
    public Room toEntity(RoomRequest request, Hotel hotel) {
        if (request == null) {
            return null;
        }
        
        Room room = new Room();
        room.setId(request.getId());
        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setRoomPrice(request.getRoomPrice());
        room.setRoomCapacity(request.getRoomCapacity());
        room.setRoomAvailability(request.getRoomAvailability());
        room.setHotel(hotel);
        
        return room;
    }
    
    public void updateEntity(Room existing, RoomRequest request) {
        if (request == null || existing == null) {
            return;
        }
        
        if (request.getRoomNumber() != null) {
            existing.setRoomNumber(request.getRoomNumber());
        }
        if (request.getRoomType() != null) {
            existing.setRoomType(request.getRoomType());
        }
        if (request.getRoomPrice() != null) {
            existing.setRoomPrice(request.getRoomPrice());
        }
        if (request.getRoomCapacity() != null) {
            existing.setRoomCapacity(request.getRoomCapacity());
        }
        if (request.getRoomAvailability() != null) {
            existing.setRoomAvailability(request.getRoomAvailability());
        }
        // Note: hotelId in request should not update the hotel association in updates
    }
}
