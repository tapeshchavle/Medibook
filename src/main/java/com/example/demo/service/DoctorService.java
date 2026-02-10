package com.example.demo.service;

import com.example.demo.model.Doctor;
import com.example.demo.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }

    public List<Doctor> searchDoctors(String query) {
        return doctorRepository.findByNameContainingIgnoreCaseOrSpecialtyContainingIgnoreCase(query, query);
    }

    public List<String> getDistinctSpecialties() {
        return doctorRepository.findDistinctSpecialties();
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Long id, Doctor doctorDetails) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        doctor.setName(doctorDetails.getName());
        doctor.setSpecialty(doctorDetails.getSpecialty());
        doctor.setImage(doctorDetails.getImage());
        doctor.setRating(doctorDetails.getRating());
        doctor.setReviews(doctorDetails.getReviews());
        doctor.setExperience(doctorDetails.getExperience());
        doctor.setAvailability(doctorDetails.getAvailability());
        doctor.setFee(doctorDetails.getFee());
        doctor.setBio(doctorDetails.getBio());
        doctor.setNextSlot(doctorDetails.getNextSlot());
        doctor.setHospital(doctorDetails.getHospital());

        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
}
