package com.example.demo.repository;

import com.example.demo.model.Appointment;
import com.example.demo.model.Appointment.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByPatientIdAndStatus(Long patientId, AppointmentStatus status);

    long countByPatientIdAndStatus(Long patientId, AppointmentStatus status);
}
