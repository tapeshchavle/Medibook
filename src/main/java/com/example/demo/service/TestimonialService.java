package com.example.demo.service;

import com.example.demo.model.Testimonial;
import com.example.demo.repository.TestimonialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestimonialService {

    private final TestimonialRepository testimonialRepository;

    public TestimonialService(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    public List<Testimonial> getAllTestimonials() {
        return testimonialRepository.findAll();
    }

    public Optional<Testimonial> getTestimonialById(Long id) {
        return testimonialRepository.findById(id);
    }

    public Testimonial createTestimonial(Testimonial testimonial) {
        return testimonialRepository.save(testimonial);
    }

    public void deleteTestimonial(Long id) {
        testimonialRepository.deleteById(id);
    }
}
