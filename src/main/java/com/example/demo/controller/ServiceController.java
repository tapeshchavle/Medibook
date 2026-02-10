package com.example.demo.controller;

import com.example.demo.model.MedicalService;
import com.example.demo.service.MedicalServiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final MedicalServiceService medicalServiceService;

    public ServiceController(MedicalServiceService medicalServiceService) {
        this.medicalServiceService = medicalServiceService;
    }

    @GetMapping
    public List<MedicalService> getAllServices() {
        return medicalServiceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalService> getServiceById(@PathVariable Long id) {
        return medicalServiceService.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MedicalService createService(@RequestBody MedicalService service) {
        return medicalServiceService.createService(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalService> updateService(@PathVariable Long id, @RequestBody MedicalService service) {
        try {
            return ResponseEntity.ok(medicalServiceService.updateService(id, service));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        medicalServiceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
