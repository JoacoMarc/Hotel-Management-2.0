# Configuración de la Base de Datos

## Setup Inicial

1. Copia el archivo `application.properties.example` a `application.properties`:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```

2. Edita el archivo `application.properties` con tus credenciales reales:
   - URL de la base de datos
   - Usuario y contraseña
   - Secret key para JWT

## Variables de Entorno (Alternativa Recomendada)

También puedes usar variables de entorno en lugar de hardcodear las credenciales:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/hotel_management}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD:password}
application.security.jwt.secretKey=${JWT_SECRET:your-secret-key}
```

## Importante

- **NUNCA** hagas commit del archivo `application.properties` con credenciales reales
- Usa `application.properties.example` como plantilla
- El archivo `application.properties` está en `.gitignore` para evitar commits accidentales
