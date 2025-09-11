package HotelManagement.hotel_management_app.repository;

import HotelManagement.hotel_management_app.entity.Image;
import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {
    
    // Métodos para imágenes de hoteles
    List<Image> findByHotel(Hotel hotel);
    
    List<Image> findByHotelId(UUID hotelId);
    
    @Query("SELECT i FROM Image i WHERE i.hotel.id = :hotelId AND i.isPrimary = true")
    Optional<Image> findPrimaryImageByHotelId(@Param("hotelId") UUID hotelId);
    
    List<Image> findByHotelIdAndIsPrimary(UUID hotelId, Boolean isPrimary);
    
    // Métodos para imágenes de habitaciones
    List<Image> findByRoom(Room room);
    
    List<Image> findByRoomId(UUID roomId);
    
    @Query("SELECT i FROM Image i WHERE i.room.id = :roomId AND i.isPrimary = true")
    Optional<Image> findPrimaryImageByRoomId(@Param("roomId") UUID roomId);
    
    List<Image> findByRoomIdAndIsPrimary(UUID roomId, Boolean isPrimary);
    
    // Métodos generales
    List<Image> findByIsPrimary(Boolean isPrimary);
    
    // Buscar por nombre de imagen
    Optional<Image> findByImageName(String imageName);
    
    // Eliminar imágenes por hotel
    void deleteByHotelId(UUID hotelId);
    
    // Eliminar imágenes por habitación
    void deleteByRoomId(UUID roomId);
}
