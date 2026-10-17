package com.example.DhanaBackEnd.Service;

import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.DTO.UpdateStaffDto;
import com.example.DhanaBackEnd.Model.Attendance;
import com.example.DhanaBackEnd.Model.Staff;
import com.example.DhanaBackEnd.Repo.AttendanceRepo;
import com.example.DhanaBackEnd.Repo.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StaffService {
      @Autowired
      public StaffRepo staffrepo;
      @Autowired
      public AttendanceRepo attendancerepo;
      public ResponseGlobal<?> getMyProfile(String email) {
             Staff staff= staffrepo.findByEmail(email);
          if  (staff == null) {
              return ResponseGlobal.onFailure("Staff not found");
          }
             return ResponseGlobal.onSuccess("profile fetched Successfully",staff);
      }
      public ResponseGlobal<?>  updateMyProfile(String email, UpdateStaffDto dto) {
          Staff staff= staffrepo.findByEmail(email);
          if  (staff == null) {
              return ResponseGlobal.onFailure("Staff not found");
          }
          staff.setPhone(dto.getPhone());
          staff.setName(dto.getName());
          staff.setDesignation(dto.getDesignation());
          staff.setDepartment(dto.getDepartment());
          staff.setEmail(dto.getEmail());
          return ResponseGlobal.onSuccess("update Profile fetched Successfully",staffrepo.save(staff));
      }
      public ResponseGlobal<?> checkIn(String email) {
          Staff staff= staffrepo.findByEmail(email);
          if  (staff == null) {
              return ResponseGlobal.onFailure("Staff not found");
          }
          Attendance attendance1=attendancerepo.findByStaff(staff);
          if (attendance1==null) {
              Attendance newattendance=new Attendance();
              newattendance.setStaff(staff);
              newattendance.setAttendanceDate( LocalDate.now());
              newattendance.setCheckIn(LocalDateTime.now());
              attendancerepo.save(newattendance);
          }
          LocalDate today= LocalDate.now();
          boolean alreadyCheckIn= attendancerepo.existsByStaffIdAndAttendanceDate(staff.getId(),today);
          if (alreadyCheckIn) {
               return ResponseGlobal.onFailure("Already checked in today");
          }
          Attendance attendance=new Attendance();
          attendance.setAttendanceDate(today);
          attendance.setCheckIn(LocalDateTime.now());
          return ResponseGlobal.onSuccess("check in Successful",attendancerepo.save(attendance));

      }
    public ResponseGlobal<?> checkOut(String email) {
        Staff staff= staffrepo.findByEmail(email);
        if  (staff == null) {
            return ResponseGlobal.onFailure("Staff not found");
        }
        LocalDate today= LocalDate.now();
        Attendance attendance = attendancerepo.findByStaffIdAndAttendanceDate(staff.getId(),today);
        if (attendance==null) {
            return ResponseGlobal.onFailure("please checked in today");
        }
        if (attendance.getCheckOut()!=null) {
            return ResponseGlobal.onFailure("Already checked out today");
        }
        LocalDateTime checkOutTime = LocalDateTime.now();
        attendance.setCheckOut(checkOutTime);
        long minutes=java.time.Duration.between(attendance.getCheckIn(),checkOutTime).toMinutes();
        attendance.setWorkingHours(minutes/60.0);
        return ResponseGlobal.onSuccess("check in Successful",attendancerepo.save(attendance));

    }
    public ResponseGlobal<?> getAttendance(String email) {
        Staff staff= staffrepo.findByEmail(email);
        if  (staff == null) {
            return ResponseGlobal.onFailure("Attendance not found");
        }
        List<Attendance> attendance=attendancerepo.findByStaffId(staff.getId());
        if (attendance.isEmpty()) {
                return ResponseGlobal.onFailure("No attendance records found");
        }
        return ResponseGlobal.onSuccess("Attendance fetched Successfully",attendance);
    }

}