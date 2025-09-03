package HotelManagement.hotel_management_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import HotelManagement.hotel_management_app.entity.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {
    
    // Buscar hoteles por país
    List<Hotel> findByCountry(String country);
    
    // Buscar hoteles por ciudad
    List<Hotel> findByCity(String city);
    
    // Buscar hoteles por estado
    List<Hotel> findByState(String state);
    
    // Buscar hoteles por tipo
    List<Hotel> findByHotelType(String hotelType);
    
    // Buscar hotel por nombre
    List<Hotel> findByName(String name);
    
    // Buscar hoteles por rating mínimo
    List<Hotel> findByRatingGreaterThanEqual(int rating);

    List<Hotel> findByNameAndCity(String name, String city);
    
        // Búsqueda con múltiples filtros opcionales
    @Query("SELECT h FROM Hotel h WHERE " +
           "(:country IS NULL OR h.country = :country) AND " +
           "(:city IS NULL OR h.city = :city) AND " +
           "(:state IS NULL OR h.state = :state) AND " +
           "(:type IS NULL OR h.hotelType = :type) AND " +
           "(:name IS NULL OR h.name LIKE %:name%) AND " +
           "(:minRating IS NULL OR h.rating >= :minRating) AND " +
           "(:maxRating IS NULL OR h.rating <= :maxRating) AND " +
           "(:zipCode IS NULL OR h.zipCode = :zipCode) AND " +
           "(:phone IS NULL OR h.phone = :phone) AND " +
           "(:email IS NULL OR h.email = :email)")
    List<Hotel> searchHotels(@Param("country") String country,
                            @Param("city") String city,
                            @Param("state") String state,
                            @Param("type") String type,
                            @Param("name") String name,
                            @Param("minRating") Integer minRating,
                            @Param("maxRating") Integer maxRating,
                            @Param("zipCode") String zipCode,
                            @Param("phone") String phone,
                            @Param("email") String email);
}
