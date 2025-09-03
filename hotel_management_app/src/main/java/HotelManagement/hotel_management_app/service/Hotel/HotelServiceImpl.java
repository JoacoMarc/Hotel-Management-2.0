package HotelManagement.hotel_management_app.service.Hotel;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.Room;
import HotelManagement.hotel_management_app.repository.HotelRepository;
import HotelManagement.hotel_management_app.exceptions.HotelDuplicateException;
import HotelManagement.hotel_management_app.exceptions.HotelNotFoundException;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(UUID id) {
        return hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException());
    }

    public Hotel createHotel(Hotel hotel) throws HotelDuplicateException {
        List<Hotel> hotels = hotelRepository.findByNameAndCity(hotel.getName(), hotel.getCity());
        if (hotels.stream().anyMatch(h -> h.getName().equals(hotel.getName()) && 
            h.getCity().equals(hotel.getCity()))) {
            throw new HotelDuplicateException();
        }
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(UUID id, Hotel hotel) {
        Hotel existingHotel = hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException());
        existingHotel.setName(hotel.getName());
        existingHotel.setAddress(hotel.getAddress());
        existingHotel.setPhone(hotel.getPhone());
        existingHotel.setEmail(hotel.getEmail());
        existingHotel.setWebsite(hotel.getWebsite());
        existingHotel.setRating(hotel.getRating());
        existingHotel.setCountry(hotel.getCountry());
        existingHotel.setCity(hotel.getCity());
        existingHotel.setState(hotel.getState());
        existingHotel.setZipCode(hotel.getZipCode());
        existingHotel.setHotelType(hotel.getHotelType());
        return hotelRepository.save(existingHotel);
    }

    public void deleteHotel(UUID id) {
        hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException());
        hotelRepository.deleteById(id);
    }

    @Override
    public List<Hotel> searchHotels(String country, String city, String state, String type, String name, Integer minRating, Integer maxRating, String zipCode, String phone, String email) {
        return hotelRepository.searchHotels(country, city, state, type, name, minRating, maxRating, zipCode, phone, email);
    }
}
