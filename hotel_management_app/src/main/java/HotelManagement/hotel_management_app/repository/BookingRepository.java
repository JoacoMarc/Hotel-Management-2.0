package HotelManagement.hotel_management_app.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import HotelManagement.hotel_management_app.entity.Booking;
import HotelManagement.hotel_management_app.entity.Guest;
import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.BookingStatus;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    
    // Buscar reservas por huésped (Many-to-Many)
    @Query("SELECT b FROM Booking b JOIN b.guests g WHERE g = :guest")
    List<Booking> findByGuest(@Param("guest") Guest guest);
    
    @Query("SELECT b FROM Booking b JOIN b.guests g WHERE g.id = :guestId")
    List<Booking> findByGuestId(@Param("guestId") UUID guestId);
    
    // Buscar reservas por hotel
    List<Booking> findByHotel(Hotel hotel);
    List<Booking> findByHotelId(UUID hotelId);
    
    // Buscar reservas por fechas
    List<Booking> findByCheckInDate(LocalDate checkInDate);
    List<Booking> findByCheckOutDate(LocalDate checkOutDate);
    List<Booking> findByCheckInDateBetween(LocalDate startDate, LocalDate endDate);
    List<Booking> findByCheckOutDateBetween(LocalDate startDate, LocalDate endDate);
    
    // Buscar reservas por estado
    List<Booking> findByStatus(BookingStatus status);
    
    // Buscar reservas por rango de fechas
    @Query("SELECT b FROM Booking b WHERE b.checkInDate <= :endDate AND b.checkOutDate >= :startDate")
    List<Booking> findBookingsInDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Buscar reservas que incluyen habitaciones específicas
    @Query("SELECT b FROM Booking b JOIN b.rooms r WHERE r.id = :roomId")
    List<Booking> findBookingsWithRoom(@Param("roomId") UUID roomId);
    
    // Validar conflictos de reserva (mismo huésped, fechas superpuestas)
    @Query("SELECT b FROM Booking b JOIN b.guests g WHERE g.id = :guestId AND " +
           "((b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate))")
    List<Booking> findConflictingBookings(@Param("guestId") UUID guestId, 
                                         @Param("checkInDate") LocalDate checkInDate,
                                         @Param("checkOutDate") LocalDate checkOutDate);
    
    // Buscar reservas activas (checked-in)
    List<Booking> findByStatusAndHotelId(BookingStatus status, UUID hotelId);
    
    // Buscar reservas por precio total
    List<Booking> findByTotalPriceBetween(double minPrice, double maxPrice);
    
    // Validar conflictos de habitaciones (misma habitación, fechas superpuestas)
    // PERMITE: check-out de booking existente = check-in de nuevo booking (mismo día)
    @Query("SELECT b FROM Booking b JOIN b.rooms r WHERE r.id = :roomId AND " +
           "((b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate))")
    List<Booking> findRoomConflictingBookings(@Param("roomId") UUID roomId, 
                                             @Param("checkInDate") LocalDate checkInDate,
                                             @Param("checkOutDate") LocalDate checkOutDate);
}