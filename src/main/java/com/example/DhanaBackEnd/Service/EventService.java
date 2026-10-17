package com.example.DhanaBackEnd.Service;

import com.example.DhanaBackEnd.Configuration.JWTService;
import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.DTO.EventDto;
import com.example.DhanaBackEnd.DTO.HolidayDto;
import com.example.DhanaBackEnd.Model.Admin;
import com.example.DhanaBackEnd.Model.Event;
import com.example.DhanaBackEnd.Model.Holiday;
import com.example.DhanaBackEnd.Repo.AdminRepo;
import com.example.DhanaBackEnd.Repo.EventRepo;
import com.example.DhanaBackEnd.Repo.HolidayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
@Service
public class EventService {
    @Autowired
    private EventRepo eventrepo;
    @Autowired
    private AdminRepo adminrepo;
    @Value("${app.upload-dir:UploadedImages}")
    private String uploadDir;
    @Autowired
    private JWTService jwtService;
    private long adminId;

    public ResponseGlobal<?> createEvent(EventDto dto) {
        try {
            if (dto.getEventName() == null || dto.getEventName().isBlank()) {
                return ResponseGlobal.onError("Event name is required");
            }
            if (dto.getEventDate() == null) {
                return ResponseGlobal.onError("Event date is required");
            }
            Admin admin = adminrepo.findById(adminId).orElse(null);
            if (admin == null) {
                return ResponseGlobal.onError("Authenticated admin not found "+adminId);
            }
            Event event = new Event();
            event.setEventName(dto.getEventName());
            event.setEventDate(dto.getEventDate());
            event.setEventDescription(dto.getEventDescription());
            event.setAdmin(admin);
            MultipartFile image = dto.getEventImage();
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
                event.setEventImage(filename);
            }
            Event saved = eventrepo.save(event);
            return ResponseGlobal.onSuccess("Event created successfully", saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseGlobal.onError("Failed to create Event" + e.getMessage());
        }
    }

    public ResponseGlobal<?> getAllEvent(String token) {
        String getEmailfromToken = jwtService.getEmailFromToken(token);
        try {
            List<Event> events = eventrepo.findAll();
            return ResponseGlobal.onSuccess("Event retrieved successfully", events);
        } catch (Exception e) {
            return ResponseGlobal.onError("Failed to retrieve Event");
        }
    }

    public ResponseGlobal<?> getEventById(Long id, String token) {
        String getEmailfromToken = jwtService.getEmailFromToken(token);
        try {
            return eventrepo.findById(id)
                    .<ResponseGlobal<?>>map(event ->
                            ResponseGlobal.onSuccess("Event retrieved successfully", event))
                    .orElseGet(() ->
                            ResponseGlobal.onError("Event not found"));

        } catch (Exception e) {
            return ResponseGlobal.onError("Failed to retrieve event");
        }
    }

    public ResponseGlobal<?> updateEvent(Long id, EventDto dto, String token) {
        String getEmailfromToken = jwtService.getEmailFromToken(token);
        try {
            Event events = eventrepo.findById(id).orElse(null);
            if (events == null) {
                return ResponseGlobal.onError("Event not found");
            }
            if (dto.getEventName() != null && !dto.getEventName().isBlank()) {
                events.setEventName(dto.getEventName());
            }
            if (dto.getEventDate() != null) {
                events.setEventDate(dto.getEventDate());
            }
            MultipartFile image = dto.getEventImage();
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
                events.setEventImage(filename);
            }
            Event updated = eventrepo.save(events);
            return ResponseGlobal.onSuccess("Event updated successfully", updated);
        } catch (Exception e) {
            return ResponseGlobal.onError("Failed to update Event: " + e.getMessage());
        }
    }

    private void deleteImageFile(String oldFileName) {
    }

    private void saveImage(MultipartFile image, Event event) {
    }

    public ResponseGlobal<?> deleteEvent(Long id, String token) {
        String getEmailfromToken = jwtService.getEmailFromToken(token);
        try {
            Event event = eventrepo.findById(id)
                    .orElse(null);
            if (event == null) {
                return ResponseGlobal.onError("Event not found");
            }
            eventrepo.delete(event);
            return ResponseGlobal.onSuccess("Event deleted successfully", null);
        } catch (Exception e) {
            return ResponseGlobal.onError("Failed to delete event");

        }
    }
}