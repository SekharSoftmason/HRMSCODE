package com.example.DhanaBackEnd.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDto {
    private long totalStaff;
    private long activeStaff;
    private long presentToday;
    private long absentToday;

}
