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
import org.springframework.web.bind.annotation.RestController;

import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.Room;
import HotelManagement.hotel_management_app.service.Hotel.HotelService;

@RestController
@RequestMapping("/hotels")
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

    // Búsquedas específicas de habitaciones por ubicación/tipo
    @GetMapping("/country/{country}/rooms")
    public List<Room> getRoomsByCountry(@PathVariable String country) {
        return hotelService.getRoomsByCountry(country);
    }

    @GetMapping("/city/{city}/rooms")
    public List<Room> getRoomsByCity(@PathVariable String city) {
        return hotelService.getRoomsByCity(city);
    }

    @GetMapping("/state/{state}/rooms")
    public List<Room> getRoomsByState(@PathVariable String state) {
        return hotelService.getRoomsByState(state);
    }

    @GetMapping("/type/{hotelType}/rooms")
    public List<Room> getRoomsByHotelType(@PathVariable String hotelType) {
        return hotelService.getRoomsByHotelType(hotelType);
    }
}