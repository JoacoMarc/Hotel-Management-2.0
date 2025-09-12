package HotelManagement.hotel_management_app.service.bookingService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import HotelManagement.hotel_management_app.entity.Booking;
import HotelManagement.hotel_management_app.entity.User;
import HotelManagement.hotel_management_app.entity.dto.bookingDTO.BookingRequest;

public interface BookingService {
    
    List<Booking> getAllBookings();
    Booking getBookingById(UUID id);
    Booking createBooking(Booking booking);
    Booking createBookingFromRequest(BookingRequest bookingRequest); // Nuevo método
    Booking updateBooking(UUID id, Booking booking);
    void deleteBooking(UUID id);
    List<Booking> getBookingsByUserId(UUID userId);
    List<Booking> getBookingsByHotelId(UUID hotelId);
    List<Booking> getBookingsByRoomId(UUID roomId);
    // Método de búsqueda con múltiples filtros opcionales
    List<Booking> searchBookings(LocalDate checkInDate, LocalDate checkOutDate, String status, UUID hotelId, UUID guestId, UUID roomId, Double minPrice, Double maxPrice, String guestSurname, 
    String guestName, String guestDocumentNumber);
    
    // Métodos de conveniencia para obtener usuarios
    List<User> getUsersByHotelId(UUID hotelId);
    List<User> getUsersByRoomId(UUID roomId);
    
    // Método para verificar conflictos de habitaciones
    List<Booking> findRoomConflictingBookings(UUID roomId, LocalDate checkInDate, LocalDate checkOutDate);
}
