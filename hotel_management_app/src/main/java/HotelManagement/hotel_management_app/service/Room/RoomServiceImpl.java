package HotelManagement.hotel_management_app.service.room;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HotelManagement.hotel_management_app.entity.Room;
import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.dto.imgDTO.ImageResponse;
import HotelManagement.hotel_management_app.entity.dto.roomDTO.RoomResponse;
import HotelManagement.hotel_management_app.repository.RoomRepository;
import HotelManagement.hotel_management_app.repository.HotelRepository;
import HotelManagement.hotel_management_app.repository.BookingRepository;
import HotelManagement.hotel_management_app.service.Img.ImageService;
import HotelManagement.hotel_management_app.exceptions.hotelExceptions.HotelNotFoundException;
import HotelManagement.hotel_management_app.exceptions.roomExceptions.RoomDuplicateException;
import HotelManagement.hotel_management_app.exceptions.roomExceptions.RoomHasActiveBookingsException;
import HotelManagement.hotel_management_app.exceptions.roomExceptions.RoomNotFoundException;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private HotelRepository hotelRepository;
    
    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private ImageService imageService;

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
        Room room = roomRepository.findById(id).orElseThrow(() -> new RoomNotFoundException());
        
        // Verificar si la habitación tiene reservas activas
        if (bookingRepository.findBookingsWithRoom(id).size() > 0) {
            throw new RoomHasActiveBookingsException("No se puede eliminar la habitación porque tiene reservas activas. Cancela todas las reservas primero.");
        }
        
        roomRepository.deleteById(id);
    }

    public List<Room> getRoomsByHotelId(UUID hotelId) throws HotelNotFoundException {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException());
        return roomRepository.findByHotel(hotel);
    }

    @Override
    public List<Room> searchRooms(String type, Double price, Integer capacity, Boolean available, String number, UUID hotelId, Double minPrice, Double maxPrice, String hotelName, String hotelCity, String hotelCountry) {
        return roomRepository.searchRooms(type, price, capacity, available, number, hotelId, minPrice, maxPrice, hotelName, hotelCity, hotelCountry);
    }
    
    // Nuevos métodos para incluir imágenes
    @Override
    public List<RoomResponse> getAllRoomsWithImages() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(this::convertToResponseWithImages)
                .collect(Collectors.toList());
    }
    
    @Override
    public RoomResponse getRoomByIdWithImages(UUID id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException());
        return convertToResponseWithImages(room);
    }
    
    @Override
    public List<RoomResponse> getRoomsByHotelIdWithImages(UUID hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException());
        List<Room> rooms = roomRepository.findByHotel(hotel);
        return rooms.stream()
                .map(this::convertToResponseWithImages)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<RoomResponse> searchRoomsWithImages(String type, Double price, Integer capacity, Boolean available, String number, UUID hotelId, Double minPrice, Double maxPrice, String hotelName, String hotelCity, String hotelCountry) {
        List<Room> rooms = roomRepository.searchRooms(type, price, capacity, available, number, hotelId, minPrice, maxPrice, hotelName, hotelCity, hotelCountry);
        return rooms.stream()
                .map(this::convertToResponseWithImages)
                .collect(Collectors.toList());
    }
    
    @Override
    public RoomResponse convertToResponseWithImages(Room room) {
        // Obtener imágenes de la habitación
        List<ImageResponse> images = imageService.getImageResponsesByRoomId(room.getId());
        ImageResponse primaryImage = imageService.getPrimaryImageResponseByRoomId(room.getId());
        String primaryImageUrl = primaryImage != null ? "/api/v1/images/" + primaryImage.getId() : null;
        
         // Construir y retornar el RoomResponse
        
        return RoomResponse.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .roomPrice(room.getRoomPrice())
                .roomCapacity(room.getRoomCapacity())
                .roomAvailability(room.isRoomAvailability())
                .hotelId(room.getHotel() != null ? room.getHotel().getId() : null)
                .hotelName(room.getHotel() != null ? room.getHotel().getName() : null)
                .images(images)
                .primaryImage(primaryImage)
                .primaryImageUrl(primaryImageUrl)
                .totalImages(images != null ? images.size() : 0)
                .build();
    }
}