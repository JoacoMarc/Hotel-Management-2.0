package HotelManagement.hotel_management_app.service.Guest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import HotelManagement.hotel_management_app.entity.Guest;
import HotelManagement.hotel_management_app.entity.dto.GuestRequest;
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
    
    @Override
    public Guest createGuest(GuestRequest guestRequest) throws GuestDuplicateException {
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
        
        // Validar duplicado por número de documento y apellido
        if (guest.getDocumentNumber() != null && !guest.getDocumentNumber().trim().isEmpty() && guest.getSurname() != null && !guest.getSurname().trim().isEmpty()) {
            List<Guest> existingGuests = guestRepository.findByDocumentNumberAndSurname(guest.getDocumentNumber(), guest.getSurname());
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

    @Override
    public List<Guest> searchGuests(String documentNumber, String surname, String email, String nationality, String name, String documentType, String phone) {
        return guestRepository.searchGuests(documentNumber, surname, email, nationality, name, documentType, phone);
    }
}