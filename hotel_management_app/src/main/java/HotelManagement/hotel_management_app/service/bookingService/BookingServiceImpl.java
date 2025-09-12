package HotelManagement.hotel_management_app.service.bookingService;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import HotelManagement.hotel_management_app.entity.Booking;
import HotelManagement.hotel_management_app.entity.User;
import HotelManagement.hotel_management_app.entity.dto.bookingDTO.BookingRequest;
import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.Room;
import HotelManagement.hotel_management_app.entity.BookingStatus;
import HotelManagement.hotel_management_app.repository.BookingRepository;
import HotelManagement.hotel_management_app.repository.UserRepository;
import HotelManagement.hotel_management_app.repository.HotelRepository;
import HotelManagement.hotel_management_app.service.hotelService.HotelService;
import HotelManagement.hotel_management_app.service.roomService.RoomService;
import HotelManagement.hotel_management_app.service.userService.UserService;
import HotelManagement.hotel_management_app.exceptions.bookingExceptions.BookingDuplicateException;
import HotelManagement.hotel_management_app.exceptions.bookingExceptions.BookingNotFoundException;
import HotelManagement.hotel_management_app.exceptions.bookingExceptions.InvalidBookingStatusException;
import HotelManagement.hotel_management_app.exceptions.hotelExceptions.HotelNotFoundException;

@Service
public class BookingServiceImpl implements BookingService {

        @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private HotelService hotelService;
    
    @Autowired
    private RoomService roomService;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(UUID id) {
        return bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException());
    }

    public Booking createBooking(Booking booking) throws BookingDuplicateException {
        // Validar que los usuarios existen
        if (booking.getGuests() != null && !booking.getGuests().isEmpty()) {
            for (User user : booking.getGuests()) {
                if (user.getId() != null) {
                    userRepository.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                }
            }
        }
        
        // Validar que el hotel existe
        if (booking.getHotel() != null && booking.getHotel().getId() != null) {
            hotelRepository.findById(booking.getHotel().getId())
                .orElseThrow(() -> new HotelNotFoundException());
        }
        
        // VALIDAR DISPONIBILIDAD Y CONFLICTOS DE HABITACIONES
        if (booking.getRooms() != null && !booking.getRooms().isEmpty()) {
            for (Room room : booking.getRooms()) {
                // 1. Verificar que la habitación esté disponible
                if (!room.isRoomAvailability()) {
                    throw new BookingDuplicateException(
                        "Room " + room.getRoomNumber() + " is not available"
                    );
                }
                
                // 2. Verificar conflictos de fechas en la habitación
                List<Booking> roomConflicts = findRoomConflictingBookings(
                    room.getId(), 
                    booking.getCheckInDate(), 
                    booking.getCheckOutDate()
                );
                if (!roomConflicts.isEmpty()) {
                    throw new BookingDuplicateException(
                        "Room " + room.getRoomNumber() + " is already booked for the selected dates"
                    );
                }
            }
            
            // 3. Validar capacidad total
            int totalGuests = booking.getGuests() != null ? booking.getGuests().size() : 0;
            int totalCapacity = booking.getRooms().stream()
                .mapToInt(Room::getRoomCapacity)
                .sum();
                
            if (totalGuests > totalCapacity) {
                throw new BookingDuplicateException(
                    String.format("Insufficient capacity. Guests: %d, Total capacity: %d", 
                    totalGuests, totalCapacity)
                );
            }
        }
        
        return bookingRepository.save(booking);
    }
    
    // Nuevo método que sigue el patrón correcto: Controller -> Service
    public Booking createBookingFromRequest(BookingRequest bookingRequest) throws BookingDuplicateException {
        // Crear el objeto Booking
        Booking booking = new Booking();
        booking.setCheckInDate(bookingRequest.getCheckInDate());
        booking.setCheckOutDate(bookingRequest.getCheckOutDate());
        booking.setTotalPrice(bookingRequest.getTotalPrice());
        booking.setStatus(BookingStatus.valueOf(bookingRequest.getStatus()));
        
        // Obtener y asignar los usuarios
        if (bookingRequest.getUserIds() != null && !bookingRequest.getUserIds().isEmpty()) {
            List<User> users = bookingRequest.getUserIds().stream()
                .map(userId -> userService.getUserById(userId))
                .toList();
            booking.setGuests(users);
        }
        
        // Obtener y asignar el hotel
        if (bookingRequest.getHotelId() != null) {
            Hotel hotel = hotelService.getHotelById(bookingRequest.getHotelId());
            booking.setHotel(hotel);
        }
        
        // Obtener y asignar las habitaciones
        if (bookingRequest.getRoomIds() != null && !bookingRequest.getRoomIds().isEmpty()) {
            List<Room> rooms = bookingRequest.getRoomIds().stream()
                .map(roomId -> roomService.getRoomById(roomId))
                .toList();
            booking.setRooms(rooms);
        }
        
        // Delegar al método createBooking que ya tiene todas las validaciones
        return createBooking(booking);
    }

    public Booking updateBooking(UUID id, Booking booking) {
        Booking existingBooking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException());
        existingBooking.setCheckInDate(booking.getCheckInDate());
        existingBooking.setCheckOutDate(booking.getCheckOutDate());
        existingBooking.setTotalPrice(booking.getTotalPrice());
        existingBooking.setStatus(booking.getStatus());
        
        // Actualizar relaciones si se proporcionan
        if (booking.getGuests() != null) {
            existingBooking.setGuests(booking.getGuests());
        }
        if (booking.getHotel() != null) {
            existingBooking.setHotel(booking.getHotel());
        }
        if (booking.getRooms() != null) {
            existingBooking.setRooms(booking.getRooms());
        }
        
        return bookingRepository.save(existingBooking);
    }

    public void deleteBooking(UUID id) {
        bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException());
        bookingRepository.deleteById(id);
    }

    public List<Booking> getBookingsByUserId(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return bookingRepository.findByUser(user);
    }
    
    

    public List<Booking> getBookingsByHotelId(UUID hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException());
        return bookingRepository.findByHotel(hotel);
    }

    public List<Booking> getBookingsByRoomId(UUID roomId) {
        return bookingRepository.findBookingsWithRoom(roomId);
    }
    
    @Override
    public List<Booking> searchBookings(LocalDate checkInDate, LocalDate checkOutDate, String status, UUID hotelId, UUID guestId, UUID roomId, Double minPrice,
     Double maxPrice, String guestSurname, String guestName, String guestDocumentNumber) {
        BookingStatus bookingStatus = null;
        if (status != null) {
            try {
                bookingStatus = BookingStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidBookingStatusException("Estado de reserva inválido: " + status);
            }
        }
        return bookingRepository.searchBookings(checkInDate, checkOutDate, bookingStatus, hotelId, guestId, roomId, minPrice, maxPrice, guestSurname, guestName, guestDocumentNumber);
    }
    
    // Métodos para obtener usuarios por hotel
    public List<User> getUsersByHotelId(UUID hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new HotelNotFoundException());
        List<Booking> bookings = bookingRepository.findByHotel(hotel);
        return bookings.stream()
                .flatMap(booking -> booking.getGuests() != null ? booking.getGuests().stream() : Stream.empty())
                .distinct()
                .collect(Collectors.toList());
    }
    
    // Métodos para obtener usuarios por habitación
    public List<User> getUsersByRoomId(UUID roomId) {
        List<Booking> bookings = bookingRepository.findBookingsWithRoom(roomId);
        return bookings.stream()
                .flatMap(booking -> booking.getGuests() != null ? booking.getGuests().stream() : Stream.empty())
                .distinct()
                .collect(Collectors.toList());
    }
    
    // Método para verificar conflictos de habitaciones
    public List<Booking> findRoomConflictingBookings(UUID roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        return bookingRepository.findRoomConflictingBookings(roomId, checkInDate, checkOutDate);
    }
}