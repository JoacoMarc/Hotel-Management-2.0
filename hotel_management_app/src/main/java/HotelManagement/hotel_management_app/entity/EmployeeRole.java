package HotelManagement.hotel_management_app.entity;

public enum EmployeeRole {
    // Roles jerárquicos de hotel
    ADMIN("Admin"),
    HOTEL_MANAGER("Hotel Manager"),
    FRONT_DESK_MANAGER("Front Desk Manager"), 
    HOUSEKEEPING_MANAGER("Housekeeping Manager"),
    ;
    
    private final String displayName;
    
    EmployeeRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
