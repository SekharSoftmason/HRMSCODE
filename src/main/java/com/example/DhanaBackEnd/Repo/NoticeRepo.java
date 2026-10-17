package com.example.DhanaBackEnd.Repo;

import com.example.DhanaBackEnd.Model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepo extends JpaRepository<Notice,Long> {
}
