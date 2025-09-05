package HotelManagement.hotel_management_app.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.service.hotel.HotelService;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    
    @GetMapping("/{hotelId}")
    public Hotel getHotelById(@PathVariable UUID hotelId) {
        return hotelService.getHotelById(hotelId);
    }

    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }

    @PutMapping("/{hotelId}")
    public Hotel updateHotel(@PathVariable UUID hotelId, @RequestBody Hotel hotel) {
        return hotelService.updateHotel(hotelId, hotel);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable UUID hotelId) {
        hotelService.deleteHotel(hotelId);
        return ResponseEntity.noContent().build();
    }

    // Búsqueda de hoteles con filtros múltiples
    @GetMapping("/search")
    public List<Hotel> searchHotels(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating,
            @RequestParam(required = false) String zipCode,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email) {
        return hotelService.searchHotels(country, city, state, type, name, minRating, maxRating, zipCode, phone, email);
    }
}