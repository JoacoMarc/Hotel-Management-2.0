package HotelManagement.hotel_management_app.service.hotelService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.Room;
import HotelManagement.hotel_management_app.entity.dto.hotelDTO.HotelResponse;
import HotelManagement.hotel_management_app.entity.dto.imgDTO.ImageResponse;
import HotelManagement.hotel_management_app.exceptions.hotelExceptions.HotelDuplicateException;
import HotelManagement.hotel_management_app.exceptions.hotelExceptions.HotelNotFoundException;
import HotelManagement.hotel_management_app.repository.HotelRepository;
import HotelManagement.hotel_management_app.service.imgService.ImageService;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    
    @Autowired
    private ImageService imageService;

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
    
    // Nuevos métodos para incluir imágenes
    @Override
    public List<HotelResponse> getAllHotelsWithImages() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream()
                .map(this::convertToResponseWithImages)
                .collect(Collectors.toList());
    }
    
    @Override
    public HotelResponse getHotelByIdWithImages(UUID hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException());
        return convertToResponseWithImages(hotel);
    }
    
    @Override
    public List<HotelResponse> searchHotelsWithImages(String country, String city, String state, String type, String name, Integer minRating, Integer maxRating, String zipCode, String phone, String email) {
        List<Hotel> hotels = hotelRepository.searchHotels(country, city, state, type, name, minRating, maxRating, zipCode, phone, email);
        return hotels.stream()
                .map(this::convertToResponseWithImages)
                .collect(Collectors.toList());
    }
    
    @Override
    public HotelResponse convertToResponseWithImages(Hotel hotel) {
        // Obtener imágenes del hotel
        List<ImageResponse> images = imageService.getImageResponsesByHotelId(hotel.getId());
        ImageResponse primaryImage = imageService.getPrimaryImageResponseByHotelId(hotel.getId());
        String primaryImageUrl = primaryImage != null ? "/api/v1/images/" + primaryImage.getId() : null;
        
        // Calcular campos adicionales (puedes personalizar según tus necesidades)
        Integer totalRooms = hotel.getRooms() != null ? hotel.getRooms().size() : 0;
        
        
        
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .phone(hotel.getPhone())
                .email(hotel.getEmail())
                .website(hotel.getWebsite())
                .rating(hotel.getRating())
                .country(hotel.getCountry())
                .city(hotel.getCity())
                .state(hotel.getState())
                .zipCode(hotel.getZipCode())
                .hotelType(hotel.getHotelType())
                .images(images)
                .primaryImage(primaryImage)
                .primaryImageUrl(primaryImageUrl)
                .totalImages(images != null ? images.size() : 0)
                .totalRooms(totalRooms)
                .build();
    }
    
    
    
    
}
