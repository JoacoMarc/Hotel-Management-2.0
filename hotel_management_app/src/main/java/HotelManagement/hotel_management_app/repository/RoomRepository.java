package HotelManagement.hotel_management_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import HotelManagement.hotel_management_app.entity.Room;
import HotelManagement.hotel_management_app.entity.Hotel;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    
    // Buscar habitaciones por hotel
    List<Room> findByHotel(Hotel hotel);
    List<Room> findByHotelId(UUID hotelId);
    
    // Buscar habitaciones por número
    List<Room> findByRoomNumber(String roomNumber);
    
    // Buscar habitaciones por número y hotel (para validar duplicados)
    List<Room> findByRoomNumberAndHotel(String roomNumber, Hotel hotel);
    List<Room> findByRoomNumberAndHotelId(String roomNumber, UUID hotelId);
    
    // Buscar habitaciones por tipo
    List<Room> findByRoomType(String roomType);
    
    // Buscar habitaciones por precio
    List<Room> findByRoomPrice(double roomPrice);
    List<Room> findByRoomPriceLessThanEqual(double maxPrice);
    List<Room> findByRoomPriceBetween(double minPrice, double maxPrice);
    
    // Buscar habitaciones por capacidad
    List<Room> findByRoomCapacity(int roomCapacity);
    List<Room> findByRoomCapacityGreaterThanEqual(int minCapacity);
    
    // Buscar habitaciones por disponibilidad
    List<Room> findByRoomAvailability(boolean roomAvailability);
    
    // Buscar habitaciones disponibles por hotel
    List<Room> findByHotelAndRoomAvailability(Hotel hotel, boolean roomAvailability);
    List<Room> findByHotelIdAndRoomAvailability(UUID hotelId, boolean roomAvailability);
}
