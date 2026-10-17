package com.example.DhanaBackEnd.Repo;

import com.example.DhanaBackEnd.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event,Long> {
}
