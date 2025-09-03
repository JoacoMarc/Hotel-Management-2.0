package HotelManagement.hotel_management_app.service.Booking;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import HotelManagement.hotel_management_app.entity.Booking;
import HotelManagement.hotel_management_app.entity.Guest;
import HotelManagement.hotel_management_app.entity.dto.BookingRequest;

public interface BookingService {
    
    List<Booking> getAllBookings();
    Booking getBookingById(UUID id);
    Booking createBooking(Booking booking);
    Booking createBookingFromRequest(BookingRequest bookingRequest); // Nuevo método
    Booking updateBooking(UUID id, Booking booking);
    void deleteBooking(UUID id);
    List<Booking> getBookingsByGuestId(UUID guestId);
    List<Booking> getBookingsByHotelId(UUID hotelId);
    List<Booking> getBookingsByRoomId(UUID roomId);
    List<Booking> getBookingsByCheckInDate(LocalDate checkInDate);
    List<Booking> getBookingsByCheckOutDate(LocalDate checkOutDate);
    List<Booking> getBookingsByStatus(String status);
    
    // Métodos de conveniencia para obtener huéspedes
    List<Guest> getGuestsByHotelId(UUID hotelId);
    List<Guest> getGuestsByRoomId(UUID roomId);
    
    // Método para verificar conflictos de habitaciones
    List<Booking> findRoomConflictingBookings(UUID roomId, LocalDate checkInDate, LocalDate checkOutDate);
}
