package com.example.demo.service;

import com.example.demo.model.MedicalService;
import com.example.demo.repository.MedicalServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalServiceService {

    private final MedicalServiceRepository medicalServiceRepository;

    public MedicalServiceService(MedicalServiceRepository medicalServiceRepository) {
        this.medicalServiceRepository = medicalServiceRepository;
    }

    public List<MedicalService> getAllServices() {
        return medicalServiceRepository.findAll();
    }

    public Optional<MedicalService> getServiceById(Long id) {
        return medicalServiceRepository.findById(id);
    }

    public MedicalService createService(MedicalService service) {
        return medicalServiceRepository.save(service);
    }

    public MedicalService updateService(Long id, MedicalService serviceDetails) {
        MedicalService service = medicalServiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
        service.setIcon(serviceDetails.getIcon());
        service.setTitle(serviceDetails.getTitle());
        service.setDescription(serviceDetails.getDescription());
        return medicalServiceRepository.save(service);
    }

    public void deleteService(Long id) {
        medicalServiceRepository.deleteById(id);
    }
}
