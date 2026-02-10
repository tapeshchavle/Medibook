package com.example.demo.repository;

import com.example.demo.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpecialty(String specialty);

    List<Doctor> findByNameContainingIgnoreCaseOrSpecialtyContainingIgnoreCase(String name, String specialty);

    @Query("SELECT DISTINCT d.specialty FROM Doctor d")
    List<String> findDistinctSpecialties();

    Optional<Doctor> findByEmail(String email);
}
