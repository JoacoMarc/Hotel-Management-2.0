package HotelManagement.hotel_management_app.controllers;
import java.net.URI;
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


public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public List<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{hotelId}")
    public Hotel getHotelById(@PathVariable UUID id) {
        return hotelService.getHotelById(id);
    }

    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }

    @PutMapping("/{hotelId}")
    public Hotel updateHotel(@PathVariable UUID id, @RequestBody Hotel hotel) {
        return hotelService.updateHotel(id, hotel);
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable UUID id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Room> getRoomsByCountry(@PathVariable String country) {
        return hotelService.getRoomsByCountry(country);
    }
    @GetMapping
    public List<Room> getRoomsByCity(@PathVariable String city) {
        return hotelService.getRoomsByCity(city);
    }
    @GetMapping
    public List<Room> getRoomsByState(@PathVariable String state) {
        return hotelService.getRoomsByState(state);
    }
    
    
    @GetMapping
    public List<Room> getRoomsByHotelType(@PathVariable String hotelType) {
        return hotelService.getRoomsByHotelType(hotelType);
    }



}
