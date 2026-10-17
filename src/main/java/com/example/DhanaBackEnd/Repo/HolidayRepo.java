package com.example.DhanaBackEnd.Repo;

import com.example.DhanaBackEnd.Model.Attendance;
import com.example.DhanaBackEnd.Model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.concurrent.atomic.LongAccumulator;

public interface HolidayRepo extends JpaRepository<Holiday, Long> {
   // List<Holiday> findAllById(long id);
}