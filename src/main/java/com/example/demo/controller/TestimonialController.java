package com.example.demo.controller;

import com.example.demo.model.Testimonial;
import com.example.demo.service.TestimonialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
public class TestimonialController {

    private final TestimonialService testimonialService;

    public TestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @GetMapping
    public List<Testimonial> getAllTestimonials() {
        return testimonialService.getAllTestimonials();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Testimonial> getTestimonialById(@PathVariable Long id) {
        return testimonialService.getTestimonialById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Testimonial createTestimonial(@RequestBody Testimonial testimonial) {
        return testimonialService.createTestimonial(testimonial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable Long id) {
        testimonialService.deleteTestimonial(id);
        return ResponseEntity.noContent().build();
    }
}
