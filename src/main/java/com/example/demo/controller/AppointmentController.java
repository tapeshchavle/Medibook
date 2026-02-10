package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.model.Appointment.AppointmentStatus;
import com.example.demo.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAllAppointments(@RequestParam(required = false) String status) {
        if (status != null && !status.isEmpty()) {
            return appointmentService.getAppointmentsByStatus(AppointmentStatus.valueOf(status.toUpperCase()));
        }
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointmentsByPatient(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatientId(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByDoctorId(doctorId);
    }

    @GetMapping("/patient/{patientId}/stats")
    public Map<String, Object> getPatientStats(@PathVariable Long patientId) {
        return appointmentService.getPatientStats(patientId);
    }

    @PostMapping
    public ResponseEntity<?> bookAppointment(@RequestBody Map<String, Object> request) {
        try {
            Long doctorId = Long.valueOf(request.get("doctorId").toString());
            Long patientId = Long.valueOf(request.get("patientId").toString());
            String date = request.get("date").toString();
            String time = request.get("time").toString();

            Appointment appointment = appointmentService.bookAppointment(doctorId, patientId, date, time);
            return ResponseEntity.ok(appointment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            AppointmentStatus status = AppointmentStatus.valueOf(request.get("status").toUpperCase());
            return ResponseEntity.ok(appointmentService.updateStatus(id, status));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        try {
            appointmentService.cancelAppointment(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
