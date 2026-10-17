package com.example.DhanaBackEnd.Service;
import com.example.DhanaBackEnd.Configuration.JWTService;
import com.example.DhanaBackEnd.DTO.HolidayDto;
import com.example.DhanaBackEnd.Model.Admin;
import com.example.DhanaBackEnd.Model.Holiday;
import com.example.DhanaBackEnd.Repo.AdminRepo;
import com.example.DhanaBackEnd.Repo.HolidayRepo;
import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
@Service
public class HolidayService {
    @Autowired
    private  HolidayRepo holidayRepo;
    @Autowired
    private AdminRepo adminrepo;
    @Value("${app.upload-dir:UploadedImages}")
    private String uploadDir;
    @Autowired
    private JWTService jwtService;
    private long adminId;

    public ResponseGlobal<?> createHoliday(HolidayDto dto,String token) {
        String getEmailfromToken=jwtService.getEmailFromToken(token);
        try {
            if (dto.getHolidayName() == null || dto.getHolidayName().isBlank()) {
                return ResponseGlobal.onError("Holiday name is required");
            }
            if (dto.getHolidayDate() == null) {
                return ResponseGlobal.onError("Holiday date is required");
            }
            Admin admin = adminrepo.findById(adminId).orElse(null);
            if (admin == null) {
                return ResponseGlobal.onError("Authenticated admin not found");
            }
            Holiday holiday = new Holiday();
            holiday.setHolidayName(dto.getHolidayName());
            holiday.setHolidayDate(dto.getHolidayDate());
            holiday.setHolidayDescription(dto.getHolidayDescription());
            holiday.setAdmin(admin);
            MultipartFile image = dto.getHolidayImage();
            if (image != null && !image.isEmpty()) {
                Path directory = Paths.get(uploadDir).toAbsolutePath().normalize();
                Files.createDirectories(directory);
                String originalName = image.getOriginalFilename();
                String extension = "";
                if (originalName != null) {
                    int dot = originalName.lastIndexOf('.');
                    if (dot >= 0) {
                        extension = originalName.substring(dot);
                    }
                }
                String filename = UUID.randomUUID() + extension;
                Path destination = directory.resolve(filename).normalize();
                if (!destination.startsWith(directory)) {
                    return ResponseGlobal.onError("Invalid image path");
                }
                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, destination);
                }
                    holiday.setHolidayImage(filename);
                }
                Holiday saved = holidayRepo.save(holiday);
                return ResponseGlobal.onSuccess("Holiday created successfully", saved);
            } catch(Exception e){
                e.printStackTrace();
                return ResponseGlobal.onError("Failed to create holiday"+e.getMessage());
            }
        }
    public ResponseGlobal<?> getAllHolidays(String token) {
        String getEmailfromToken=jwtService.getEmailFromToken(token);
        try {
            List<Holiday> holidays = holidayRepo.findAll();
            return ResponseGlobal.onSuccess("Holidays retrieved successfully", holidays);
        } catch (Exception e) {
            return ResponseGlobal.onError("Failed to retrieve holidays");
        }
    }
    public ResponseGlobal<?> getHolidayById(Long id ,String token) {
        String getEmailfromToken=jwtService.getEmailFromToken(token);
        try {
            return holidayRepo.findById(id)
                    .<ResponseGlobal<?>>map(holiday ->
                            ResponseGlobal.onSuccess("Holiday retrieved successfully", holiday))
                    .orElseGet(() ->
                            ResponseGlobal.onError("Holiday not found"));

        } catch (Exception e) {
            return ResponseGlobal.onError("Failed to retrieve holiday");
        }
    }
    public ResponseGlobal<?> updateHoliday(Long id, HolidayDto dto ,String token) {
        String getEmailfromToken=jwtService.getEmailFromToken(token);
        try {
            Holiday holiday = holidayRepo.findById(id).orElse(null);
            if (holiday == null) {
                return ResponseGlobal.onError("Holiday not found");
            }
            if (dto.getHolidayName() != null && !dto.getHolidayName().isBlank()) {
                holiday.setHolidayName(dto.getHolidayName());
            }
            if (dto.getHolidayDate() != null) {
                holiday.setHolidayDate(dto.getHolidayDate());
            }
            MultipartFile image = dto.getHolidayImage();
            if (image != null && !image.isEmpty()) {
                Path directory = Paths.get(uploadDir).toAbsolutePath().normalize();
                Files.createDirectories(directory);
                String originalName = image.getOriginalFilename();
                String extension = "";
                if (originalName != null) {
                    int dot = originalName.lastIndexOf('.');
                    if (dot >= 0) {
                        extension = originalName.substring(dot);
                    }
                }
                String filename = UUID.randomUUID() + extension;
                Path destination = directory.resolve(filename).normalize();
                if (!destination.startsWith(directory)) {
                    return ResponseGlobal.onError("Invalid image path");
                }
                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, destination);
                }
                holiday.setHolidayImage(filename);
            }
            Holiday updated = holidayRepo.save(holiday);
            return ResponseGlobal.onSuccess("Holiday updated successfully", updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGlobal.onError("Failed to update holiday: " + e.getMessage());
        }
    }

    private void deleteImageFile(String oldFileName) {
    }

    private void saveImage(MultipartFile image, Holiday holiday) {
    }

    public ResponseGlobal<?> deleteHoliday(Long id,String token ) {
        String getEmailfromToken=jwtService.getEmailFromToken(token);
        try {
            Holiday holiday = holidayRepo.findById(id)
                    .orElse(null);
            if (holiday == null) {
                return ResponseGlobal.onError("Holiday not found");
            }
            holidayRepo.delete(holiday);
            return ResponseGlobal.onSuccess("Holiday deleted successfully", null);
        } catch (Exception e) {
            return ResponseGlobal.onError("Failed to delete holiday");
        }
    }
}