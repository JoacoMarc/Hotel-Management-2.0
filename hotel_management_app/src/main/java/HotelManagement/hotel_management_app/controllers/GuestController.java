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
        // Convertir GuestRequest a Guest
        Guest guest = new Guest();
        guest.setName(guestRequest.getName());
        guest.setSurname(guestRequest.getSurname());
        guest.setEmail(guestRequest.getEmail());
        guest.setPhone(guestRequest.getPhone());
        guest.setDocumentType(guestRequest.getDocumentType());
        guest.setDocumentNumber(guestRequest.getDocumentNumber());
        guest.setNationality(guestRequest.getNationality());
        guest.setBirthDate(guestRequest.getBirthDate());
        
        return guestService.createGuest(guest);
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

    // Búsquedas específicas con rutas claras
    @GetMapping("/document/{documentNumber}")
    public List<Guest> getGuestsByDocumentNumber(@PathVariable String documentNumber) {
        return guestService.getGuestsByDocumentNumber(documentNumber);
    }

    @GetMapping("/surname/{surname}")
    public List<Guest> getGuestsBySurname(@PathVariable String surname) {
        return guestService.getGuestsBySurname(surname);
    }

    @GetMapping("/email/{email}")
    public List<Guest> getGuestsByEmail(@PathVariable String email) {
        return guestService.getGuestsByEmail(email);
    }

    @GetMapping("/name/{name}")
    public List<Guest> getGuestsByName(@PathVariable String name) {
        return guestService.getGuestsByName(name);
    }

    @GetMapping("/nationality/{nationality}")
    public List<Guest> getGuestsByNationality(@PathVariable String nationality) {
        return guestService.getGuestsByNationality(nationality);
    }
}