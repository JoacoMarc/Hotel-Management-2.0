-- Script para limpiar todas las tablas
-- Ejecutar en orden debido a las foreign keys

-- Deshabilitar verificación de foreign keys temporalmente
SET FOREIGN_KEY_CHECKS = 0;

-- Limpiar datos en orden
DELETE FROM booking_guests;
DELETE FROM bookings;
DELETE FROM employees;
DELETE FROM guests;
DELETE FROM rooms;
DELETE FROM hotels;

-- Rehabilitar verificación de foreign keys
SET FOREIGN_KEY_CHECKS = 1;

-- Verificar que las tablas estén vacías
SELECT 'hotels' as tabla, COUNT(*) as registros FROM hotels
UNION ALL
SELECT 'rooms', COUNT(*) FROM rooms
UNION ALL
SELECT 'employees', COUNT(*) FROM employees
UNION ALL
SELECT 'guests', COUNT(*) FROM guests
UNION ALL
SELECT 'bookings', COUNT(*) FROM bookings
UNION ALL
SELECT 'booking_guests', COUNT(*) FROM booking_guests;
