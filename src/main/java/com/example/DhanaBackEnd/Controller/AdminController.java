package com.example.DhanaBackEnd.Controller;

import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.DTO.StaffDto;
import com.example.DhanaBackEnd.DTO.UpdateStaffDto;
import com.example.DhanaBackEnd.Model.Attendance;
import com.example.DhanaBackEnd.Model.Staff;
import com.example.DhanaBackEnd.Service.AdminService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/admin")
public class
AdminController {
     @Autowired
     private AdminService adminservice;
     @PostMapping("/staff")
     public ResponseGlobal<?> CreateStaff( @RequestBody StaffDto staffdto) {
              return adminservice.CreateStaff(staffdto);
     }
     @GetMapping("/staff")
     public ResponseGlobal<List<?>> getAllStaff(@RequestHeader("Authorization") String token) {
             return adminservice.getAllStaff(token);
     }
     @GetMapping("/staff/{id}")
    public ResponseGlobal<?> getStaffById(@PathVariable long id) {
        return adminservice.getStaffById(id);
    }
    @PutMapping("/staff/{id}")
    public ResponseGlobal<?> updateStaff(@RequestHeader("Authorization") String authorization,@PathVariable long id,@Valid @RequestBody UpdateStaffDto request) {
        String token=authorization.startsWith("Bearer ")?authorization.substring(7):authorization;
         return adminservice.updateStaff(id,request,token);
    }
    @DeleteMapping("/staff/{id}")
    public String deleteStaff(@PathVariable long id) {
           return adminservice.deleteStaff(id);
    }
    @GetMapping("/attendance")
    public ResponseGlobal<?> getAllAttendance(String token) {
         return adminservice.getAllAttendance(token);
    }
    @GetMapping("/attendance/{staffId}")
    public ResponseGlobal<?>  getStaffAttendance(@PathVariable long staffId) {
           return adminservice.getStaffAttendance(staffId);
    }
    @GetMapping("/dashboard")
    public ResponseGlobal<?> getDashboard(@RequestParam String token) {
           return adminservice.getDashboard(token);
    }

}



