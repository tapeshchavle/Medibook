package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getAllDoctors(
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return doctorService.searchDoctors(search);
        }
        if (specialty != null && !specialty.isEmpty() && !specialty.equals("All")) {
            return doctorService.getDoctorsBySpecialty(specialty);
        }
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/specialties")
    public List<String> getSpecialties() {
        return doctorService.getDistinctSpecialties();
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorService.createDoctor(doctor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        try {
            return ResponseEntity.ok(doctorService.updateDoctor(id, doctor));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
