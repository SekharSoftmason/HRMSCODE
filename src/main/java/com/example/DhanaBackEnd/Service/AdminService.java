package com.example.DhanaBackEnd.Service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.example.DhanaBackEnd.Configuration.ResponseGlobal;
import com.example.DhanaBackEnd.Configuration.SecurityConfig;
import com.example.DhanaBackEnd.DTO.DashboardDto;
import com.example.DhanaBackEnd.DTO.StaffDto;
import com.example.DhanaBackEnd.Model.Attendance;
import com.example.DhanaBackEnd.Model.Dashboard;
import com.example.DhanaBackEnd.Model.Login;
import com.example.DhanaBackEnd.Model.Staff;
import com.example.DhanaBackEnd.Repo.AdminRepo;
import com.example.DhanaBackEnd.Repo.AttendanceRepo;
import com.example.DhanaBackEnd.Repo.LoginRepo;
import com.example.DhanaBackEnd.Repo.StaffRepo;
import com.example.DhanaBackEnd.RoleEnum.RoleEnum;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;

import static com.example.DhanaBackEnd.RoleEnum.RoleEnum.staff;


@Service
public class AdminService {
    @Autowired
    private LoginRepo loginrepo;
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private StaffRepo staffrepo;
    @Autowired
    private AttendanceRepo attendancerepo;
    public ResponseGlobal<?> CreateStaff(StaffDto staffdto) {
        if (staffdto.getEmail() == null || staffdto.getEmail().isEmpty()) {
            return ResponseGlobal.onError("The Email is Required");
        }
        Staff existStaff = staffrepo.findByEmail(staffdto.getEmail());
        if (existStaff != null) {
            return ResponseGlobal.onFailure("This Email is being attached with another staff");
        }
        Login login = new Login();
        login.setName(staffdto.getName());
        login.setEmail(staffdto.getEmail());
        login.setPassword(staffdto.getPassword());
        login.setRole("STAFF");
        login.setActive(true);
        loginrepo.save(login);

        Staff staff = new Staff();
        staff.setPhone(staffdto.getPhone());
        staff.setName(staffdto.getName());
        staff.setDesignation(staffdto.getDesignation());
        staff.setDepartment(staffdto.getDepartment());
        staff.setEmail(staffdto.getEmail());
        staff.setJoiningDate(staffdto.getJoiningDate());
        return ResponseGlobal.onSuccess("Staff created SuccessFully",staffrepo.save(staff));

    }

    public ResponseGlobal<List<Staff>> getAllStaff() {
        List<Staff> staff=staffrepo.findAll();
        if (staff.isEmpty()) {
             return ResponseGlobal.onFailure("no staff found");
        }
        return ResponseGlobal.onSuccess("Staff Fetched Successfully",staff);
    }
    public ResponseGlobal<Staff> getStaffById(long id) {
        try {
            return ResponseGlobal.onSuccess("Staff Fetched SuccessFully",
                    staffrepo.findById(id).orElseThrow(()->new RuntimeException("staff not Found" +id)));
        }
        catch(RuntimeException e) {
              throw new RuntimeException("Staff not found with id: "+id);


        }
    }

    public ResponseGlobal<Staff> updateStaff(long id, Staff staff) {
            Staff existStaff=staffrepo.findById(id).orElseThrow(()->new RuntimeException("staff not Found"));
            existStaff.setPhone(staff.getPhone());
            existStaff.setName(staff.getName());
            existStaff.setDesignation(staff.getDesignation());
            existStaff.setDepartment(staff.getDepartment());
            existStaff.setEmail(staff.getEmail());
            existStaff.setJoiningDate(staff.getJoiningDate());
            return ResponseGlobal.onSuccess("Staff updated SuccessFully", staffrepo.save(existStaff));

    }

    public String deleteStaff(Long id) {
        if (!staffrepo.existsById(id)) {
            return "Student with id " + id + " does not exist.";
        }
       staffrepo.deleteById(id);
    return "Student with id " + id + " has been deleted successfully.";
    }

    public ResponseGlobal<?> getAllAttendance() {
        List<Attendance> attendance=attendancerepo.findAll();
        if (attendancerepo==null) {
            return ResponseGlobal.onFailure("no staff found");
        }
        return ResponseGlobal.onSuccess("Staff Fetched Successfully",staff);
    }
    public ResponseGlobal<?> getStaffAttendance(long staffId) {
          if (!staffrepo.existsById(staffId)) {
               return ResponseGlobal.onFailure("staff not found with id: "+staffId);
          }
          List<Attendance> attendanceList=attendancerepo.findByStaffId(staffId);
          if (attendanceList.isEmpty()) {
              return ResponseGlobal.onFailure("No attendance records found for staff id"+ staffId);
          }
          return ResponseGlobal.onSuccess("staff attendance fetched successfully",attendanceList);

    }
    public ResponseGlobal<?> getDashboard() {
            long totalStaff= staffrepo.count();
            long activeStaff=loginrepo.countByRoleAndActive("STAFF",true);
            List<Attendance> todayAttendance=attendancerepo.findByAttendanceDate(LocalDate.now());
            long presentToday=todayAttendance.stream().filter
                    (attendance -> attendance.getCheckIn()!=null).count();
            long absentToday= Math.max(0,totalStaff-presentToday);
        DashboardDto dto =new DashboardDto(totalStaff,activeStaff,presentToday,absentToday);
        return ResponseGlobal.onSuccess("Dashboard fetched Successfully",dto);

    }

}




