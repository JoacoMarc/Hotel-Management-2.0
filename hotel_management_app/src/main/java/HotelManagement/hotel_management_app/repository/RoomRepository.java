package HotelManagement.hotel_management_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    
    // Búsqueda con múltiples filtros opcionales
    @Query("SELECT r FROM Room r JOIN r.hotel h WHERE " +
           "(:type IS NULL OR r.roomType = :type) AND " +
           "(:price IS NULL OR r.roomPrice = :price) AND " +
           "(:capacity IS NULL OR r.roomCapacity = :capacity) AND " +
           "(:available IS NULL OR r.roomAvailability = :available) AND " +
           "(:number IS NULL OR r.roomNumber = :number) AND " +
           "(:hotelId IS NULL OR r.hotel.id = :hotelId) AND " +
           "(:minPrice IS NULL OR r.roomPrice >= :minPrice) AND " +
           "(:maxPrice IS NULL OR r.roomPrice <= :maxPrice) AND " +
           "(:hotelName IS NULL OR h.name LIKE %:hotelName%) AND " +
           "(:hotelCity IS NULL OR h.city = :hotelCity) AND " +
           "(:hotelCountry IS NULL OR h.country = :hotelCountry)")
    List<Room> searchRooms(@Param("type") String type,
                          @Param("price") Double price,
                          @Param("capacity") Integer capacity,
                          @Param("available") Boolean available,
                          @Param("number") String number,
                          @Param("hotelId") UUID hotelId,
                          @Param("minPrice") Double minPrice,
                          @Param("maxPrice") Double maxPrice,
                          @Param("hotelName") String hotelName,
                          @Param("hotelCity") String hotelCity,
                          @Param("hotelCountry") String hotelCountry);
}
