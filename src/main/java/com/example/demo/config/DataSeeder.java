package com.example.demo.config;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

        private final DoctorRepository doctorRepository;
        private final MedicalServiceRepository medicalServiceRepository;
        private final TestimonialRepository testimonialRepository;
        private final PatientRepository patientRepository;
        private final PasswordEncoder passwordEncoder;

        public DataSeeder(DoctorRepository doctorRepository,
                        MedicalServiceRepository medicalServiceRepository,
                        TestimonialRepository testimonialRepository,
                        PatientRepository patientRepository,
                        PasswordEncoder passwordEncoder) {
                this.doctorRepository = doctorRepository;
                this.medicalServiceRepository = medicalServiceRepository;
                this.testimonialRepository = testimonialRepository;
                this.patientRepository = patientRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Override
        public void run(String... args) {
                if (doctorRepository.count() > 0) {
                        System.out.println("Database already seeded. Skipping...");
                        return;
                }

                System.out.println("Seeding database with initial data...");

                String defaultPassword = passwordEncoder.encode("password123");

                // Seed Doctors (with email + hashed password)
                doctorRepository.save(new Doctor("Dr. James Mitchell", "james.mitchell@medibook.com", defaultPassword,
                                "Cardiology", "/images/doctor1.png",
                                4.9, 284, "15 years", "Mon - Fri", 150.0,
                                "Board-certified cardiologist specializing in preventive cardiology and heart failure management.",
                                "Today, 2:00 PM", "MediBook Central Hospital"));

                doctorRepository.save(new Doctor("Dr. Sarah Chen", "sarah.chen@medibook.com", defaultPassword,
                                "Neurology", "/images/doctor2.png",
                                4.8, 192, "12 years", "Mon - Sat", 175.0,
                                "Expert neurologist focused on migraine treatment, epilepsy, and neurodegenerative disorders.",
                                "Today, 4:30 PM", "NeuroScience Institute"));

                doctorRepository.save(new Doctor("Dr. Alex Rivera", "alex.rivera@medibook.com", defaultPassword,
                                "Orthopedics", "/images/doctor3.png",
                                4.7, 156, "10 years", "Tue - Sat", 130.0,
                                "Sports medicine specialist with expertise in minimally invasive joint surgery and rehabilitation.",
                                "Tomorrow, 10:00 AM", "SportsMed Clinic"));

                doctorRepository.save(new Doctor("Dr. Emily Watson", "emily.watson@medibook.com", defaultPassword,
                                "Dermatology", "/images/doctor2.png",
                                4.9, 321, "18 years", "Mon - Fri", 120.0,
                                "Renowned dermatologist specializing in cosmetic procedures and skin cancer screening.",
                                "Today, 5:00 PM", "SkinCare Advanced Center"));

                doctorRepository.save(new Doctor("Dr. Michael Park", "michael.park@medibook.com", defaultPassword,
                                "Pediatrics", "/images/doctor1.png",
                                4.8, 245, "14 years", "Mon - Sat", 100.0,
                                "Compassionate pediatrician dedicated to child wellness, vaccinations, and developmental care.",
                                "Tomorrow, 9:00 AM", "Children's Health Pavilion"));

                doctorRepository.save(new Doctor("Dr. Lisa Thompson", "lisa.thompson@medibook.com", defaultPassword,
                                "Psychiatry", "/images/doctor2.png",
                                4.6, 178, "11 years", "Wed - Sun", 160.0,
                                "Psychiatrist specializing in anxiety, depression, and cognitive behavioral therapy.",
                                "Tomorrow, 2:00 PM", "MindWell Wellness Center"));

                // Seed Services
                medicalServiceRepository.save(new MedicalService("Stethoscope", "General Checkup",
                                "Comprehensive health assessments with advanced AI-powered diagnostics for early detection."));
                medicalServiceRepository.save(new MedicalService("Brain", "Neural Scanning",
                                "State-of-the-art neurological imaging and cognitive health evaluation."));
                medicalServiceRepository.save(new MedicalService("HeartPulse", "Cardiac Care",
                                "Advanced heart monitoring with real-time ECG analysis and preventive care programs."));
                medicalServiceRepository.save(new MedicalService("Dna", "Genomic Analysis",
                                "Personalized medicine through DNA sequencing and genetic risk assessment."));
                medicalServiceRepository.save(new MedicalService("Shield", "Immunotherapy",
                                "Next-generation immune system optimization and vaccination programs."));
                medicalServiceRepository.save(new MedicalService("Activity", "Rehabilitation",
                                "AI-assisted recovery programs with progress tracking and adaptive exercise plans."));

                // Seed Testimonials
                testimonialRepository.save(new Testimonial("Amanda Foster", "Patient", 5,
                                "MediBook completely transformed my healthcare experience. The booking process is seamless and the doctors are exceptional. I love the futuristic interface!",
                                "\uD83D\uDC69\u200D\uD83D\uDCBC"));
                testimonialRepository.save(new Testimonial("Robert Kim", "Patient", 5,
                                "As someone with a busy schedule, MediBook's instant booking feature is a lifesaver. The AI-driven health insights are incredibly accurate.",
                                "\uD83D\uDC68\u200D\uD83D\uDCBB"));
                testimonialRepository.save(new Testimonial("Diana Patel", "Patient", 4,
                                "The doctors on this platform are truly world-class. I appreciate the detailed profiles and genuine reviews from other patients.",
                                "\uD83D\uDC69\u200D\uD83D\uDD2C"));

                // Seed test patient (password: password123)
                patientRepository.save(new Patient("John Doe", "john@example.com", defaultPassword, "+1234567890"));

                System.out.println("Database seeded successfully!");
                System.out.println("=== Login Credentials ===");
                System.out.println("Patient: john@example.com / password123");
                System.out.println("Doctor:  james.mitchell@medibook.com / password123");
        }
}
