package com.example.DhanaBackEnd.Controller;
import com.example.DhanaBackEnd.DTO.HolidayDto;
import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.Service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
@RestController
public class HolidayController {
    @Autowired
    private HolidayService holidayService;
    @PostMapping(value ="/holiday" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseGlobal<?> createHoliday(@ModelAttribute HolidayDto holidayDto,@RequestHeader("Authorization") String token) {

        return holidayService.createHoliday(holidayDto,token);
    }
    @GetMapping("/holiday")
    public ResponseGlobal<?> getAllHolidays(@RequestHeader("Authorization") String token) {
        return holidayService.getAllHolidays(token);
    }
    @GetMapping("/holiday/{id}")
    public ResponseGlobal<?> getHolidayById(@PathVariable Long id,@RequestHeader("Authorization") String token) {
        return holidayService.getHolidayById(id,token);
    }
    @PutMapping(value = "/holiday/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseGlobal<?> updateHoliday(@PathVariable Long id, @ModelAttribute HolidayDto holidayDto,@RequestHeader("Authorization") String token) {
        return holidayService.updateHoliday(id, holidayDto,token);
    }
    @DeleteMapping("/holiday/{id}")
    public ResponseGlobal<?> deleteHoliday(@PathVariable Long id,@RequestHeader("Authorization") String token) {
        return holidayService.deleteHoliday(id,token);
    }
}