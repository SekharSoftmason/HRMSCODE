package com.example.DhanaBackEnd.Model;

import lombok.Data;

@Data
public class Dashboard {
    private long totalStaff;
    private long activeStaff;
    private long presentToday;
    private long absentToday;
}

