package com.example.DhanaBackEnd.Controller;

import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.DTO.StaffDto;
import com.example.DhanaBackEnd.Model.Attendance;
import com.example.DhanaBackEnd.Model.Staff;
import com.example.DhanaBackEnd.Service.AdminService;
import jakarta.validation.Valid;
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
     public ResponseGlobal<Staff> CreateStaff( @RequestBody StaffDto staffdto) {
              return adminservice.CreateStaff(staffdto);
     }
    @GetMapping("/test-response")
    public String testResponse() {
        return "Response is working";
    }
     @GetMapping("/staff")
     public ResponseGlobal<List<Staff>> getAllStaff() {
             return adminservice.getAllStaff();
     }
     @GetMapping("/staff/{id}")
    public ResponseGlobal<Staff> getStaffById(@PathVariable long id) {
        return adminservice.getStaffById(id);
    }
    @PostMapping("/staff/{id}")
    public ResponseGlobal<Staff> updateStaff(@Valid @PathVariable long id,@RequestBody Staff staff) {
        return adminservice.updateStaff(id,staff);
    }
    @DeleteMapping("/staff/{id}")
    public String deleteStaff(@PathVariable long id) {
           return adminservice.deleteStaff(id);
    }
    @GetMapping("/attendance/{staffId}")
    public ResponseGlobal<?>  getStaffAttendance(@PathVariable long staffId) {
           return adminservice.getStaffAttendance(staffId);
    }
    @GetMapping("/dashboard")
    public ResponseGlobal<?> getDashboard() {
           return adminservice.getDashboard();
    }

}



