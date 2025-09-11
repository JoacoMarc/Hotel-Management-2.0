package HotelManagement.hotel_management_app.service.Img;

import HotelManagement.hotel_management_app.entity.Image;
import HotelManagement.hotel_management_app.entity.Hotel;
import HotelManagement.hotel_management_app.entity.Room;
import HotelManagement.hotel_management_app.entity.dto.imgDTO.ImageRequest;
import HotelManagement.hotel_management_app.entity.dto.imgDTO.ImageResponse;
import HotelManagement.hotel_management_app.repository.ImageRepository;
import HotelManagement.hotel_management_app.repository.HotelRepository;
import HotelManagement.hotel_management_app.repository.RoomRepository;
import HotelManagement.hotel_management_app.exceptions.hotelExceptions.HotelNotFoundException;
import HotelManagement.hotel_management_app.exceptions.roomExceptions.RoomNotFoundException;
import HotelManagement.hotel_management_app.exceptions.imageExceptions.ImageNotFoundException;
import HotelManagement.hotel_management_app.exceptions.imageExceptions.ImageUploadException;
import HotelManagement.hotel_management_app.exceptions.imageExceptions.ImageStorageException;
import HotelManagement.hotel_management_app.exceptions.imageExceptions.EmptyImageFileException;
import HotelManagement.hotel_management_app.exceptions.imageExceptions.InvalidImageTypeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    
    @Autowired
    private ImageRepository imageRepository;
    
    @Autowired
    private HotelRepository hotelRepository;
    
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Image create(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Image viewById(UUID id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Imagen no encontrada con id: " + id));
    }

    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        imageRepository.deleteById(id);
    }

    @Override
    public Image uploadImageForHotel(UUID hotelId, MultipartFile file, String imageName, Boolean isPrimary) {
        try {
            // Validar archivo
            if (file == null || file.isEmpty()) {
                throw new EmptyImageFileException("El archivo de imagen está vacío o es nulo");
            }
            
            // Validar tipo de archivo
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new InvalidImageTypeException("El tipo de archivo no es válido. Solo se permiten imágenes");
            }

            Hotel hotel = hotelRepository.findById(hotelId)
                    .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + hotelId));

            // Si es imagen principal, desmarcar las otras como principal
            if (isPrimary != null && isPrimary) {
                List<Image> currentPrimaryImages = imageRepository.findByHotelIdAndIsPrimary(hotelId, true);
                currentPrimaryImages.forEach(img -> {
                    img.setIsPrimary(false);
                    imageRepository.save(img);
                });
            }

            Blob imageBlob = new SerialBlob(file.getBytes());
            
            Image image = Image.builder()
                    .imageName(imageName != null ? imageName : file.getOriginalFilename())
                    .imageType(file.getContentType())
                    .image(imageBlob)
                    .hotel(hotel)
                    .isPrimary(isPrimary != null ? isPrimary : false)
                    .build();

            return imageRepository.save(image);
        } catch (IOException e) {
            throw new ImageUploadException("Error al leer el archivo de imagen: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new ImageStorageException("Error al almacenar la imagen en la base de datos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ImageRequest> getImagesByHotelId(UUID hotelId) {
        List<Image> images = imageRepository.findByHotelId(hotelId);
        return images.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ImageRequest getPrimaryImageByHotelId(UUID hotelId) {
        Optional<Image> primaryImage = imageRepository.findPrimaryImageByHotelId(hotelId);
        return primaryImage.map(this::convertToDto).orElse(null);
    }

    @Override
    public void deleteImagesByHotelId(UUID hotelId) {
        imageRepository.deleteByHotelId(hotelId);
    }

    @Override
    public Image uploadImageForRoom(UUID roomId, MultipartFile file, String imageName, Boolean isPrimary) {
        try {
            // Validar archivo
            if (file == null || file.isEmpty()) {
                throw new EmptyImageFileException("El archivo de imagen está vacío o es nulo");
            }
            
            // Validar tipo de archivo
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new InvalidImageTypeException("El tipo de archivo no es válido. Solo se permiten imágenes");
            }

            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

            // Si es imagen principal, desmarcar las otras como principal
            if (isPrimary != null && isPrimary) {
                List<Image> currentPrimaryImages = imageRepository.findByRoomIdAndIsPrimary(roomId, true);
                currentPrimaryImages.forEach(img -> {
                    img.setIsPrimary(false);
                    imageRepository.save(img);
                });
            }

            Blob imageBlob = new SerialBlob(file.getBytes());
            
            Image image = Image.builder()
                    .imageName(imageName != null ? imageName : file.getOriginalFilename())
                    .imageType(file.getContentType())
                    .image(imageBlob)
                    .room(room)
                    .isPrimary(isPrimary != null ? isPrimary : false)
                    .build();

            return imageRepository.save(image);
        } catch (IOException e) {
            throw new ImageUploadException("Error al leer el archivo de imagen: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new ImageStorageException("Error al almacenar la imagen en la base de datos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ImageRequest> getImagesByRoomId(UUID roomId) {
        List<Image> images = imageRepository.findByRoomId(roomId);
        return images.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ImageRequest getPrimaryImageByRoomId(UUID roomId) {
        Optional<Image> primaryImage = imageRepository.findPrimaryImageByRoomId(roomId);
        return primaryImage.map(this::convertToDto).orElse(null);
    }

    @Override
    public void deleteImagesByRoomId(UUID roomId) {
        imageRepository.deleteByRoomId(roomId);
    }

    @Override
    public ImageRequest convertToDto(Image image) {
        return ImageRequest.builder()
                .id(image.getId())
                .imageName(image.getImageName())
                .imageType(image.getImageType())
                .imageUrl("/api/v1/images/" + image.getId()) // URL para acceder a la imagen
                .hotelId(image.getHotel() != null ? image.getHotel().getId() : null)
                .roomId(image.getRoom() != null ? image.getRoom().getId() : null)
                .isPrimary(image.getIsPrimary())
                .build();
    }

    @Override
    public byte[] getImageData(UUID imageId) {
        try {
            Image image = viewById(imageId);
            Blob blob = image.getImage();
            return blob.getBytes(1, (int) blob.length());
        } catch (SQLException e) {
            throw new ImageStorageException("Error al recuperar los datos de la imagen: " + e.getMessage(), e);
        }
    }

    @Override
    public void setPrimaryImage(UUID imageId, UUID hotelId, UUID roomId) {
        Image image = viewById(imageId);
        
        if (hotelId != null && image.getHotel() != null && image.getHotel().getId().equals(hotelId)) {
            // Desmarcar otras imágenes del hotel como principal
            List<Image> currentPrimaryImages = imageRepository.findByHotelIdAndIsPrimary(hotelId, true);
            currentPrimaryImages.forEach(img -> {
                img.setIsPrimary(false);
                imageRepository.save(img);
            });
        }
        
        if (roomId != null && image.getRoom() != null && image.getRoom().getId().equals(roomId)) {
            // Desmarcar otras imágenes de la habitación como principal
            List<Image> currentPrimaryImages = imageRepository.findByRoomIdAndIsPrimary(roomId, true);
            currentPrimaryImages.forEach(img -> {
                img.setIsPrimary(false);
                imageRepository.save(img);
            });
        }
        
        image.setIsPrimary(true);
        imageRepository.save(image);
    }

    @Override
    public ImageResponse convertToResponse(Image image) {
        return ImageResponse.builder()
                .id(image.getId())
                .imageName(image.getImageName())
                .imageType(image.getImageType())
                .imageUrl("/api/v1/images/" + image.getId())
                .isPrimary(image.getIsPrimary())
                .build();
    }

    @Override
    public ImageResponse getImageWithBase64(UUID id) {
        try {
            Image image = viewById(id);
            String encodedString = Base64.getEncoder()
                    .encodeToString(getImageData(id));
            
            return ImageResponse.builder()
                    .id(id)
                    .imageName(image.getImageName())
                    .imageType(image.getImageType())
                    .imageUrl("/api/v1/images/" + id)
                    .isPrimary(image.getIsPrimary())
                    .file(encodedString) // Campo legacy
                    .build();
        } catch (Exception e) {
            throw new ImageStorageException("Error al obtener la imagen codificada en base64: " + e.getMessage(), e);
        }
    }
    
    // Nuevos métodos para devolver ImageResponse
    @Override
    public List<ImageResponse> getImageResponsesByHotelId(UUID hotelId) {
        List<Image> images = imageRepository.findByHotelId(hotelId);
        return images.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public ImageResponse getPrimaryImageResponseByHotelId(UUID hotelId) {
        Optional<Image> primaryImage = imageRepository.findPrimaryImageByHotelId(hotelId);
        return primaryImage.map(this::convertToResponse).orElse(null);
    }
    
    @Override
    public List<ImageResponse> getImageResponsesByRoomId(UUID roomId) {
        List<Image> images = imageRepository.findByRoomId(roomId);
        return images.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    @Override
    public ImageResponse getPrimaryImageResponseByRoomId(UUID roomId) {
        Optional<Image> primaryImage = imageRepository.findPrimaryImageByRoomId(roomId);
        return primaryImage.map(this::convertToResponse).orElse(null);
    }
}
