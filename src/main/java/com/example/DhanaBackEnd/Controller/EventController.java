package com.example.DhanaBackEnd.Controller;

import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.DTO.EventDto;
import com.example.DhanaBackEnd.DTO.HolidayDto;
import com.example.DhanaBackEnd.Service.EventService;
import com.example.DhanaBackEnd.Service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
@RestController
public class EventController {
    @Autowired
    private EventService eventService;
    @PostMapping(value ="/event" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseGlobal<?> createEvent(@ModelAttribute EventDto eventDto) {
        return eventService.createEvent(eventDto);
    }
    @GetMapping("/event")
    public ResponseGlobal<?> getAllEvent(@RequestHeader("Authorization") String token) {
        return eventService.getAllEvent(token);
    }
    @GetMapping("/event/{id}")
    public ResponseGlobal<?> getEventById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        return eventService.getEventById(id,token);
    }
    @PutMapping(value = "/event/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseGlobal<?> updateEvent(@PathVariable Long id, @ModelAttribute EventDto eventDto,@RequestHeader("Authorization") String token) {
        return eventService.updateEvent(id, eventDto,token);
    }
    @DeleteMapping("/event/{id}")
    public ResponseGlobal<?> deleteEvent(@PathVariable Long id,@RequestHeader("Authorization") String token) {
        return eventService.deleteEvent(id,token);
    }
}
