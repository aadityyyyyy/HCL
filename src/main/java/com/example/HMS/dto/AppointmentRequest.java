package com.example.HMS.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private Long doctorId;
    private LocalDateTime appointmentDate;
    private String notes;
}
