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

import HotelManagement.hotel_management_app.entity.Guest;
import HotelManagement.hotel_management_app.entity.dto.GuestRequest;
import HotelManagement.hotel_management_app.service.Guest.GuestService;

@RestController
@RequestMapping("/guests")
public class GuestController {
    @Autowired
    private GuestService guestService;

    @GetMapping
    public List<Guest> getAllGuests() {
        return guestService.getAllGuests();
    }
    
    @GetMapping("/{guestId}")
    public Guest getGuestById(@PathVariable UUID guestId) {
        return guestService.getGuestById(guestId);
    }

    @PostMapping
    public Guest createGuest(@RequestBody GuestRequest guestRequest) {
        return guestService.createGuest(guestRequest);
    }

    @PutMapping("/{guestId}")
    public Guest updateGuest(@PathVariable UUID guestId, @RequestBody Guest guest) {
        return guestService.updateGuest(guestId, guest);
    }

    @DeleteMapping("/{guestId}")
    public ResponseEntity<Void> deleteGuest(@PathVariable UUID guestId) {
        guestService.deleteGuest(guestId);
        return ResponseEntity.noContent().build();
    }

    // Búsqueda con filtros múltiples usando query parameters
    @GetMapping("/search")
    public List<Guest> searchGuests(
            @RequestParam(required = false) String documentNumber,
            @RequestParam(required = false) String surname, 
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nationality,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String documentType,
            @RequestParam(required = false) String phone) {
        return guestService.searchGuests(documentNumber, surname, email, nationality, name, documentType, phone);
    }
}