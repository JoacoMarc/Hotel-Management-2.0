package HotelManagement.hotel_management_app.service.Guest;

import java.util.List;
import java.util.UUID;

import HotelManagement.hotel_management_app.entity.Guest;

public interface GuestService {

    List<Guest> getAllGuests();
    Guest getGuestById(UUID id);
    Guest createGuest(Guest guest);
    Guest updateGuest(UUID id, Guest guest);
    void deleteGuest(UUID id);
    List<Guest> getGuestsByDocumentNumber(String documentNumber);
    List<Guest> getGuestsBySurname(String surname);
    List<Guest> getGuestsByEmail(String email);
    List<Guest> getGuestsByName(String name);
    List<Guest> getGuestsByNationality(String nationality);
    List<Guest> getGuestsByNameAndSurname(String name, String surname);
}