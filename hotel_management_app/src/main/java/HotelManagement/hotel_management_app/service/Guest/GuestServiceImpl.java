package HotelManagement.hotel_management_app.service.Guest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HotelManagement.hotel_management_app.entity.Guest;
import HotelManagement.hotel_management_app.repository.GuestRepository;
import HotelManagement.hotel_management_app.exceptions.GuestNotFoundException;
import HotelManagement.hotel_management_app.exceptions.GuestDuplicateException;

@Service
public class GuestServiceImpl implements GuestService {

    @Autowired
    private GuestRepository guestRepository;

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public Guest getGuestById(UUID id) {
        return guestRepository.findById(id).orElseThrow(() -> new GuestNotFoundException());
    }
    
    public Guest createGuest(Guest guest) throws GuestDuplicateException {
        // Validar duplicado por número de documento
        if (guest.getDocumentNumber() != null && !guest.getDocumentNumber().trim().isEmpty()) {
            List<Guest> existingGuests = guestRepository.findByDocumentNumber(guest.getDocumentNumber());
            if (!existingGuests.isEmpty()) {
                throw new GuestDuplicateException();
            }
        }
        return guestRepository.save(guest);
    }

    public Guest updateGuest(UUID id, Guest guest) {
        Guest existingGuest = guestRepository.findById(id).orElseThrow(() -> new GuestNotFoundException());
        existingGuest.setName(guest.getName());
        existingGuest.setSurname(guest.getSurname());
        existingGuest.setEmail(guest.getEmail());
        existingGuest.setPhone(guest.getPhone());
        existingGuest.setDocumentType(guest.getDocumentType());
        existingGuest.setDocumentNumber(guest.getDocumentNumber());
        existingGuest.setNationality(guest.getNationality());
        existingGuest.setBirthDate(guest.getBirthDate());
        return guestRepository.save(existingGuest);
    }

    public void deleteGuest(UUID id) {
        guestRepository.findById(id).orElseThrow(() -> new GuestNotFoundException());
        guestRepository.deleteById(id);
    }

    public List<Guest> getGuestsByDocumentNumber(String documentNumber) {
        return guestRepository.findByDocumentNumber(documentNumber);
    }

    public List<Guest> getGuestsBySurname(String surname) {
        return guestRepository.findBySurname(surname);
    }

    public List<Guest> getGuestsByEmail(String email) {
        return guestRepository.findByEmail(email);
    }
    
    public List<Guest> getGuestsByName(String name) {
        return guestRepository.findByName(name);
    }
    
    public List<Guest> getGuestsByNationality(String nationality) {
        return guestRepository.findByNationality(nationality);
    }
    
    public List<Guest> getGuestsByNameAndSurname(String name, String surname) {
        return guestRepository.findByNameAndSurname(name, surname);
    }
}