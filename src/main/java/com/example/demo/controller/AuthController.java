package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.model.Role;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AuthController(AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
        }

        // Check if patient
        Optional<Patient> patient = patientRepository.findByEmail(request.getEmail());
        if (patient.isPresent()) {
            Patient p = patient.get();
            String token = jwtUtil.generateToken(p.getEmail(), Role.PATIENT.name(), p.getId());
            return ResponseEntity
                    .ok(new LoginResponse(token, Role.PATIENT.name(), p.getName(), p.getId(), p.getEmail()));
        }

        // Check if doctor
        Optional<Doctor> doctor = doctorRepository.findByEmail(request.getEmail());
        if (doctor.isPresent()) {
            Doctor d = doctor.get();
            String token = jwtUtil.generateToken(d.getEmail(), Role.DOCTOR.name(), d.getId());
            return ResponseEntity
                    .ok(new LoginResponse(token, Role.DOCTOR.name(), d.getName(), d.getId(), d.getEmail()));
        }

        return ResponseEntity.status(401).body(Map.of("error", "User not found"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerPatient(@RequestBody RegisterRequest request) {
        if (patientRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
        }
        if (doctorRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered as doctor"));
        }

        Patient patient = new Patient(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getPhone());
        patient = patientRepository.save(patient);

        String token = jwtUtil.generateToken(patient.getEmail(), Role.PATIENT.name(), patient.getId());
        return ResponseEntity.ok(
                new LoginResponse(token, Role.PATIENT.name(), patient.getName(), patient.getId(), patient.getEmail()));
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<?> registerDoctor(@RequestBody RegisterRequest request) {
        if (doctorRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered"));
        }
        if (patientRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already registered as patient"));
        }

        Doctor doctor = new Doctor(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getSpecialty(),
                request.getImage(),
                0.0, 0,
                request.getExperience(),
                request.getAvailability(),
                request.getFee(),
                request.getBio(),
                "Available",
                request.getHospital());
        doctor = doctorRepository.save(doctor);

        String token = jwtUtil.generateToken(doctor.getEmail(), Role.DOCTOR.name(), doctor.getId());
        return ResponseEntity
                .ok(new LoginResponse(token, Role.DOCTOR.name(), doctor.getName(), doctor.getId(), doctor.getEmail()));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        String role = jwtUtil.extractRole(token);

        if (Role.PATIENT.name().equals(role)) {
            Optional<Patient> patient = patientRepository.findByEmail(email);
            if (patient.isPresent()) {
                Patient p = patient.get();
                Map<String, Object> result = new HashMap<>();
                result.put("id", p.getId());
                result.put("name", p.getName());
                result.put("email", p.getEmail());
                result.put("phone", p.getPhone());
                result.put("role", Role.PATIENT.name());
                return ResponseEntity.ok(result);
            }
        } else {
            Optional<Doctor> doctor = doctorRepository.findByEmail(email);
            if (doctor.isPresent()) {
                Doctor d = doctor.get();
                Map<String, Object> result = new HashMap<>();
                result.put("id", d.getId());
                result.put("name", d.getName());
                result.put("email", d.getEmail());
                result.put("specialty", d.getSpecialty());
                result.put("role", Role.DOCTOR.name());
                return ResponseEntity.ok(result);
            }
        }

        return ResponseEntity.notFound().build();
    }
}
