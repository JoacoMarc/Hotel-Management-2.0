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
import HotelManagement.hotel_management_app.entity.dto.BookingResponse;
import HotelManagement.hotel_management_app.service.Booking.BookingService;
import HotelManagement.hotel_management_app.service.Booking.BookingMapper;
import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private BookingMapper bookingMapper;

    @GetMapping
    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        return bookings.stream()
                .map(bookingMapper::toBookingResponse)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{bookingId}")
    public BookingResponse getBookingById(@PathVariable UUID bookingId) {
        Booking booking = bookingService.getBookingById(bookingId);
        return bookingMapper.toBookingResponse(booking);
    }
    
    @PostMapping
    public BookingResponse createBooking(@RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.createBookingFromRequest(bookingRequest);
        return bookingMapper.toBookingResponse(booking);
    }
    
    @PutMapping("/{bookingId}")
    public BookingResponse updateBooking(@PathVariable UUID bookingId, @RequestBody Booking booking) {
        Booking updatedBooking = bookingService.updateBooking(bookingId, booking);
        return bookingMapper.toBookingResponse(updatedBooking);
    }
    
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable UUID bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
    
    // Búsquedas específicas con rutas claras
    @GetMapping("/user/{userId}")
    public List<BookingResponse> getBookingsByUserId(@PathVariable UUID userId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        return bookings.stream()
                .map(bookingMapper::toBookingResponse)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/hotel/{hotelId}")
    public List<BookingResponse> getBookingsByHotelId(@PathVariable UUID hotelId) {
        List<Booking> bookings = bookingService.getBookingsByHotelId(hotelId);
        return bookings.stream()
                .map(bookingMapper::toBookingResponse)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/room/{roomId}")
    public List<BookingResponse> getBookingsByRoomId(@PathVariable UUID roomId) {
        List<Booking> bookings = bookingService.getBookingsByRoomId(roomId);
        return bookings.stream()
                .map(bookingMapper::toBookingResponse)
                .collect(Collectors.toList());
    }
    
    // Búsqueda con filtros múltiples usando query parameters
    @GetMapping("/search")
    public List<BookingResponse> searchBookings(
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
        List<Booking> bookings = bookingService.searchBookings(parsedCheckInDate, parsedCheckOutDate, status, hotelId, guestId, roomId, minPrice, maxPrice, guestSurname, 
        guestName, guestDocumentNumber);
        return bookings.stream()
                .map(bookingMapper::toBookingResponse)
                .collect(Collectors.toList());
    }
}