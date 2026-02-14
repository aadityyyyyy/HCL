package com.example.HMS.service;
import com.example.HMS.dto.AppointmentRequest;
import com.example.HMS.model.*;
import com.example.HMS.repository.AppointmentRepository;
import com.example.HMS.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    // Patient books appointment
    public Appointment bookAppointment(AppointmentRequest request, User patient) {


         if (patient.getRole() != Role.PATIENT) {
        throw new RuntimeException("Only patients can book appointments");
    }

        User doctor = userRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

                if (doctor.getRole() != Role.DOCTOR) {
        throw new RuntimeException("Selected user is not a doctor");
    }

        Appointment appointment = Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentDate(request.getAppointmentDate())
                .status(AppointmentStatus.PENDING)
                .notes(request.getNotes())
                .build();

        return appointmentRepository.save(appointment);
    }

    // Patient views own appointments
    public List<Appointment> getPatientAppointments(User patient) {
        return appointmentRepository.findByPatient(patient);
    }

    // Doctor views appointments
    public List<Appointment> getDoctorAppointments(User doctor) {
        return appointmentRepository.findByDoctor(doctor);
        
    }

    // Doctor updates status
    public Appointment updateStatus(Long id, AppointmentStatus status, User doctor) {

    if (doctor.getRole() != Role.DOCTOR) {
        throw new RuntimeException("Only doctors can update appointment status");
    }

    Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));

    appointment.setStatus(status);
    return appointmentRepository.save(appointment);
}

}
