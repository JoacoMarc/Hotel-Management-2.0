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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import HotelManagement.hotel_management_app.entity.Booking;
import HotelManagement.hotel_management_app.entity.dto.BookingRequest;
import HotelManagement.hotel_management_app.service.Booking.BookingService;
import java.time.LocalDate;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }
    
    @GetMapping("/{bookingId}")
    public Booking getBookingById(@PathVariable UUID bookingId) {
        return bookingService.getBookingById(bookingId);
    }
    
    @PostMapping
    public Booking createBooking(@RequestBody BookingRequest bookingRequest) {
        return bookingService.createBookingFromRequest(bookingRequest);
    }
    
    @PutMapping("/{bookingId}")
    public Booking updateBooking(@PathVariable UUID bookingId, @RequestBody Booking booking) {
        return bookingService.updateBooking(bookingId, booking);
    }
    
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable UUID bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
    
    // Búsquedas específicas con rutas claras
    @GetMapping("/guest/{guestId}")
    public List<Booking> getBookingsByGuestId(@PathVariable UUID guestId) {
        return bookingService.getBookingsByGuestId(guestId);
    }
    
    @GetMapping("/hotel/{hotelId}")
    public List<Booking> getBookingsByHotelId(@PathVariable UUID hotelId) {
        return bookingService.getBookingsByHotelId(hotelId);
    }
    
    @GetMapping("/room/{roomId}")
    public List<Booking> getBookingsByRoomId(@PathVariable UUID roomId) {
        return bookingService.getBookingsByRoomId(roomId);
    }
    
    // Búsqueda con filtros múltiples usando query parameters
    @GetMapping("/search")
    public List<Booking> searchBookings(
            @RequestParam(required = false) String checkInDate,
            @RequestParam(required = false) String checkOutDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) UUID hotelId,
            @RequestParam(required = false) UUID guestId,
            @RequestParam(required = false) UUID roomId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String guestSurname,
            @RequestParam(required = false) String guestDocumentNumber,
            @RequestParam(required = false) String guestName) {
        LocalDate parsedCheckInDate = checkInDate != null ? LocalDate.parse(checkInDate) : null;
        LocalDate parsedCheckOutDate = checkOutDate != null ? LocalDate.parse(checkOutDate) : null;
        return bookingService.searchBookings(parsedCheckInDate, parsedCheckOutDate, status, hotelId, guestId, roomId, minPrice, maxPrice, guestSurname, 
        guestName, guestDocumentNumber);
    }
}