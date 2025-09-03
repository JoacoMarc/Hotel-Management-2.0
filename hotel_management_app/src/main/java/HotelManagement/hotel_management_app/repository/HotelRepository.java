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
}
