package com.example.HMS.dto;

import com.example.HMS.model.AppointmentStatus;
import lombok.Data;

@Data
public class AppointmentStatusRequest {
    private AppointmentStatus status;
}
