package HotelManagement.hotel_management_app.service.Hotel;

import java.util.List;
import java.util.UUID;

import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.Room;

public interface HotelService {
    List<Hotel> getAllHotels();
    Hotel getHotelById(UUID id);
    Hotel createHotel(Hotel hotel);
    Hotel updateHotel(UUID id, Hotel hotel);
    void deleteHotel(UUID id);
    // Método de búsqueda de hoteles con múltiples filtros opcionales
    List<Hotel> searchHotels(String country, String city, String state, String type, String name, Integer minRating, Integer maxRating, String zipCode, String phone, String email);
}
