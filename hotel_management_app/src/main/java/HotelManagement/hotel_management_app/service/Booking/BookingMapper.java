package HotelManagement.hotel_management_app.service.booking;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import HotelManagement.hotel_management_app.entity.Booking;
import HotelManagement.hotel_management_app.entity.Room;
import HotelManagement.hotel_management_app.entity.User;
import HotelManagement.hotel_management_app.entity.dto.bookingDTO.BookingResponse;

@Component
public class BookingMapper {
    
    public BookingResponse toBookingResponse(Booking booking) {
        return BookingResponse.builder()
            .id(booking.getId())
            .checkInDate(booking.getCheckInDate())
            .checkOutDate(booking.getCheckOutDate())
            .totalPrice(booking.getTotalPrice())
            .status(booking.getStatus())
            .guests(booking.getGuests().stream()
                .map(this::toGuestInfo)
                .collect(Collectors.toList()))
            .hotel(toHotelInfo(booking))
            .rooms(booking.getRooms().stream()
                .map(this::toRoomInfo)
                .collect(Collectors.toList()))
            .build();
    }
    
    private BookingResponse.GuestInfo toGuestInfo(User user) {
        return BookingResponse.GuestInfo.builder()
            .id(user.getId())
            .name(user.getName())
            .surname(user.getSurname())
            .email(user.getEmail())
            .phone(user.getPhone())
            .build();
    }
    
    private BookingResponse.HotelInfo toHotelInfo(Booking booking) {
        if (booking.getHotel() == null) return null;
        
        return BookingResponse.HotelInfo.builder()
            .id(booking.getHotel().getId())
            .name(booking.getHotel().getName())
            .address(booking.getHotel().getAddress())
            .phone(booking.getHotel().getPhone())
            .city(booking.getHotel().getCity())
            .country(booking.getHotel().getCountry())
            .build();
    }
    
    private BookingResponse.RoomInfo toRoomInfo(Room room) {
        return BookingResponse.RoomInfo.builder()
            .id(room.getId())
            .roomNumber(room.getRoomNumber())
            .roomType(room.getRoomType())
            .roomPrice(room.getRoomPrice())
            .roomCapacity(room.getRoomCapacity())
            .build();
    }
}
