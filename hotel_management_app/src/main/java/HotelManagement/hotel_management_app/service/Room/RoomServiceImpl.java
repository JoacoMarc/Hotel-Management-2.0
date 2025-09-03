package HotelManagement.hotel_management_app.service.Room;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HotelManagement.hotel_management_app.entity.Room;
import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.repository.RoomRepository;
import HotelManagement.hotel_management_app.repository.HotelRepository;
import HotelManagement.hotel_management_app.exceptions.RoomNotFoundException;
import HotelManagement.hotel_management_app.exceptions.RoomDuplicateException;
import HotelManagement.hotel_management_app.exceptions.HotelNotFoundException;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private HotelRepository hotelRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(UUID id) {
        return roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException());
    }

    public Room createRoom(Room room) throws RoomDuplicateException {
        // Validar que el hotel existe
        if (room.getHotel() != null && room.getHotel().getId() != null) {
            hotelRepository.findById(room.getHotel().getId())
                .orElseThrow(() -> new HotelNotFoundException());
        }
        
        // Validar duplicado por número de habitación y hotel
        if (room.getHotel() != null) {
            List<Room> existingRooms = roomRepository.findByRoomNumberAndHotel(
                room.getRoomNumber(), room.getHotel());
            if (!existingRooms.isEmpty()) {
                throw new RoomDuplicateException();
            }
        }
        
        return roomRepository.save(room);
    }

    public Room updateRoom(UUID id, Room room) {
        Room existingRoom = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException());
        existingRoom.setRoomNumber(room.getRoomNumber());
        existingRoom.setRoomType(room.getRoomType());
        existingRoom.setRoomPrice(room.getRoomPrice());
        existingRoom.setRoomCapacity(room.getRoomCapacity());
        existingRoom.setRoomAvailability(room.isRoomAvailability());
        if (room.getHotel() != null) {
            existingRoom.setHotel(room.getHotel());
        }
        return roomRepository.save(existingRoom);
    }

    public void deleteRoom(UUID id) {
        roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException());
        roomRepository.deleteById(id);
    }

    public List<Room> getRoomsByHotelId(UUID hotelId) throws HotelNotFoundException {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException());
        return roomRepository.findByHotel(hotel);
    }

    public List<Room> getRoomsByRoomType(String roomType) {
        return roomRepository.findByRoomType(roomType);
    }

    public List<Room> getRoomsByRoomPrice(double roomPrice) {
        return roomRepository.findByRoomPrice(roomPrice);
    }

    public List<Room> getRoomsByRoomCapacity(int roomCapacity) {
        return roomRepository.findByRoomCapacity(roomCapacity);
    }

    public List<Room> getRoomsByRoomAvailability(boolean roomAvailability) {
        return roomRepository.findByRoomAvailability(roomAvailability);
    }

    public List<Room> getRoomsByRoomNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }
}