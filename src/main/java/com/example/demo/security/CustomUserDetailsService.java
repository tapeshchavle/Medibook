package com.example.demo.security;

import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.PatientRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public CustomUserDetailsService(DoctorRepository doctorRepository,
            PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try finding as patient first
        Optional<Patient> patient = patientRepository.findByEmail(email);
        if (patient.isPresent()) {
            Patient p = patient.get();
            return new User(p.getEmail(), p.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_PATIENT")));
        }

        // Try finding as doctor
        Optional<Doctor> doctor = doctorRepository.findByEmail(email);
        if (doctor.isPresent()) {
            Doctor d = doctor.get();
            return new User(d.getEmail(), d.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_DOCTOR")));
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
