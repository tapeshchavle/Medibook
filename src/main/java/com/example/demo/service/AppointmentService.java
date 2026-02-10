package com.example.demo.service;

import com.example.demo.model.Appointment;
import com.example.demo.model.Appointment.AppointmentStatus;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository,
            PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status);
    }

    public Appointment bookAppointment(Long doctorId, Long patientId, String date, String time) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        Appointment appointment = new Appointment(doctor, patient, date, time, AppointmentStatus.UPCOMING);
        return appointmentRepository.save(appointment);
    }

    public Appointment updateStatus(Long id, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }

    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    public Map<String, Object> getPatientStats(Long patientId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("upcoming", appointmentRepository.countByPatientIdAndStatus(patientId, AppointmentStatus.UPCOMING));
        stats.put("completed", appointmentRepository.countByPatientIdAndStatus(patientId, AppointmentStatus.COMPLETED));
        stats.put("cancelled", appointmentRepository.countByPatientIdAndStatus(patientId, AppointmentStatus.CANCELLED));
        stats.put("total", appointmentRepository.findByPatientId(patientId).size());
        return stats;
    }
}
