package com.example.HMS.controller;

import com.example.HMS.dto.AppointmentRequest;
import com.example.HMS.dto.AppointmentStatusRequest;
import com.example.HMS.model.Appointment;
import com.example.HMS.model.User;
import com.example.HMS.security.CustomUserDetails;
import com.example.HMS.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // PATIENT → book appointment
    @PostMapping
    public Appointment bookAppointment(
            @RequestBody AppointmentRequest request,
            Authentication authentication
    ) {
        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User patient = userDetails.getUser();

        return appointmentService.bookAppointment(request, patient);
    }

    // PATIENT → view own appointments
    @GetMapping("/my")
    public List<Appointment> myAppointments(Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User patient = userDetails.getUser();

        return appointmentService.getPatientAppointments(patient);
    }

    // DOCTOR → view assigned appointments
    @GetMapping("/doctor")
    public List<Appointment> doctorAppointments(Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User doctor = userDetails.getUser();

        return appointmentService.getDoctorAppointments(doctor);
    }

    // DOCTOR → update appointment status
   @PutMapping("/{id}/status")
public Appointment updateStatus(
        @PathVariable Long id,
        @RequestBody AppointmentStatusRequest request,
        Authentication authentication
) {
    CustomUserDetails userDetails =
            (CustomUserDetails) authentication.getPrincipal();

    User doctor = userDetails.getUser();

    return appointmentService.updateStatus(id, request.getStatus(), doctor);
}

}
