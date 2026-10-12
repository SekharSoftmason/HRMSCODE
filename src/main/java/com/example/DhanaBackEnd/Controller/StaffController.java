package com.example.DhanaBackEnd.Controller;

import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.DTO.UpdateStaffDto;
import com.example.DhanaBackEnd.Service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('staff')")
@RequestMapping("/staff")
public class StaffController {
          @Autowired
          private StaffService staffservice;
          @GetMapping("/profile")
          public ResponseGlobal<?> getMyProfile(Authentication authentication) {
              String email= authentication.getName();
                 return staffservice.getMyProfile(email);
          }
          @PutMapping("/profile")
          public ResponseGlobal<?> updateMyProfile(Authentication authentication ,@RequestBody UpdateStaffDto dto) {
              String email= authentication.getName();
              return staffservice.updateMyProfile(email,dto);
          }
          @PostMapping("/check-out")
        public ResponseGlobal<?> checkIn(Authentication authentication) {
        String email= authentication.getName();
        return staffservice.checkIn(email);
    }
    @PostMapping("/check-In")
    public ResponseGlobal<?> checkOut(Authentication authentication) {
        String email= authentication.getName();
        return staffservice.checkOut(email);
    }
    @GetMapping("/attendance")
    public ResponseGlobal<?> getAttendance(Authentication authentication) {
        String email= authentication.getName();
        return staffservice.getAttendance(email);
    }

}
