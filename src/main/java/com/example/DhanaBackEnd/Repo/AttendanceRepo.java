package com.example.DhanaBackEnd.Repo;

import com.example.DhanaBackEnd.Model.Attendance;
import com.example.DhanaBackEnd.Model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStaffId(long staffId);

    List<Attendance> findByAttendanceDate(LocalDate now);
    boolean existsByStaffIdAndAttendanceDate(Long staffId,LocalDate attendanceDate);
    Attendance findByStaffIdAndAttendanceDate(Long staffId,LocalDate attendanceDate);
}
