package com.example.DhanaBackEnd.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NoticeDto {
    private Long adminId;
    private String noticeTitle;
    private String noticeName;
    private String noticeDescription;
}
