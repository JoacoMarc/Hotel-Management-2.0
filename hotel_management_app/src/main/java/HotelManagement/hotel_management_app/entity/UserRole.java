package HotelManagement.hotel_management_app.entity;

public enum UserRole {
    // Administrador del sistema
    ADMIN("Admin"),
    
    // Roles de empleados por hotel
    HOTEL_MANAGER("Hotel Manager"),
    FRONT_DESK_MANAGER("Front Desk Manager"), 
    HOUSEKEEPING_MANAGER("Housekeeping Manager"),
    RECEPTIONIST("Receptionist"),
    HOUSEKEEPER("Housekeeper"),
    
    // Huésped
    GUEST("Guest");
    
    private final String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}

