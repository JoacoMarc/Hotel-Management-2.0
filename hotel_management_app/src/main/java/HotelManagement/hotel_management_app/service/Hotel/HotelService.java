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
    List<Room> getRoomsByCountry(String country);
    List<Room> getRoomsByCity(String city);
    List<Room> getRoomsByState(String state);
    List<Room> getRoomsByHotelType(String hotelType);
}
