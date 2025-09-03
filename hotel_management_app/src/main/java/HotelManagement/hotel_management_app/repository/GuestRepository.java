package HotelManagement.hotel_management_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import HotelManagement.hotel_management_app.entity.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, UUID> {
    
    // Buscar huéspedes por nombre
    List<Guest> findByName(String name);
    List<Guest> findByNameContainingIgnoreCase(String name);
    
    // Buscar huéspedes por apellido
    List<Guest> findBySurname(String surname);
    List<Guest> findBySurnameContainingIgnoreCase(String surname);
    
    // Buscar huéspedes por nombre completo
    List<Guest> findByNameAndSurname(String name, String surname);
    
    // Buscar huéspedes por email
    List<Guest> findByEmail(String email);
    
    // Buscar huéspedes por teléfono
    List<Guest> findByPhone(String phone);
    
    // Buscar huéspedes por documento
    List<Guest> findByDocumentNumber(String documentNumber);
    List<Guest> findByDocumentTypeAndDocumentNumber(String documentType, String documentNumber);
    
    // Buscar huéspedes por nacionalidad
    List<Guest> findByNationality(String nationality);
}
