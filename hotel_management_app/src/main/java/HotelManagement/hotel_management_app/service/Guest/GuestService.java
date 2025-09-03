package HotelManagement.hotel_management_app.service.Guest;

import java.util.List;
import java.util.UUID;

import HotelManagement.hotel_management_app.entity.Guest;
import HotelManagement.hotel_management_app.entity.dto.GuestRequest;

public interface GuestService {

    List<Guest> getAllGuests();
    Guest getGuestById(UUID id);
    Guest createGuest(GuestRequest guestRequest);
    Guest updateGuest(UUID id, Guest guest);
    void deleteGuest(UUID id);
    // Método de búsqueda con múltiples filtros opcionales
    List<Guest> searchGuests(String documentNumber, String surname, String email, String nationality, String name, String documentType, String phone);
}