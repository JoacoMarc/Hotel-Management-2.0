package HotelManagement.hotel_management_app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    List<Guest> findByDocumentNumberAndSurname(String documentNumber, String surname);
    
    
    // Búsqueda con múltiples filtros opcionales
    @Query("SELECT g FROM Guest g WHERE " +
           "(:documentNumber IS NULL OR g.documentNumber = :documentNumber) AND " +
           "(:surname IS NULL OR g.surname LIKE %:surname%) AND " +
           "(:email IS NULL OR g.email = :email) AND " +
           "(:nationality IS NULL OR g.nationality = :nationality) AND " +
           "(:name IS NULL OR g.name LIKE %:name%) AND " +
           "(:documentType IS NULL OR g.documentType = :documentType) AND " +
           "(:phone IS NULL OR g.phone = :phone)")
    List<Guest> searchGuests(@Param("documentNumber") String documentNumber,
                            @Param("surname") String surname,
                            @Param("email") String email, 
                            @Param("nationality") String nationality,
                            @Param("name") String name,
                            @Param("documentType") String documentType,
                            @Param("phone") String phone);
}
