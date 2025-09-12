package HotelManagement.hotel_management_app.service.hotelService;

import org.springframework.stereotype.Component;

import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.dto.hotelDTO.HotelRequest;
import HotelManagement.hotel_management_app.entity.dto.hotelDTO.HotelResponse;

@Component
public class HotelMapper {
    
    public Hotel toEntity(HotelRequest request) {
        if (request == null) {
            return null;
        }
        
        Hotel hotel = new Hotel();
        hotel.setId(request.getId());
        hotel.setName(request.getName());
        hotel.setAddress(request.getAddress());
        hotel.setPhone(request.getPhone());
        hotel.setEmail(request.getEmail());
        hotel.setWebsite(request.getWebsite());
        hotel.setRating(request.getRating());
        hotel.setCountry(request.getCountry());
        hotel.setCity(request.getCity());
        hotel.setState(request.getState());
        hotel.setZipCode(request.getZipCode());
        hotel.setHotelType(request.getHotelType());
        
        return hotel;
    }
    
    public HotelResponse toResponse(Hotel hotel) {
        if (hotel == null) {
            return null;
        }
        
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
                .build();
    }
    
    public void updateEntity(Hotel existing, HotelRequest request) {
        if (request == null || existing == null) {
            return;
        }
        
        if (request.getName() != null) {
            existing.setName(request.getName());
        }
        if (request.getAddress() != null) {
            existing.setAddress(request.getAddress());
        }
        if (request.getPhone() != null) {
            existing.setPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            existing.setEmail(request.getEmail());
        }
        if (request.getWebsite() != null) {
            existing.setWebsite(request.getWebsite());
        }
        if (request.getRating() != null) {
            existing.setRating(request.getRating());
        }
        if (request.getCountry() != null) {
            existing.setCountry(request.getCountry());
        }
        if (request.getCity() != null) {
            existing.setCity(request.getCity());
        }
        if (request.getState() != null) {
            existing.setState(request.getState());
        }
        if (request.getZipCode() != null) {
            existing.setZipCode(request.getZipCode());
        }
        if (request.getHotelType() != null) {
            existing.setHotelType(request.getHotelType());
        }
    }
}
