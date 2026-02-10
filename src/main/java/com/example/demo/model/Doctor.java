package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String specialty;

    private String image;

    private Double rating;

    private Integer reviews;

    private String experience;

    private String availability;

    private Double fee;

    @Column(length = 1000)
    private String bio;

    private String nextSlot;

    private String hospital;

    public Doctor() {
    }

    public Doctor(String name, String email, String password, String specialty, String image,
            Double rating, Integer reviews, String experience, String availability,
            Double fee, String bio, String nextSlot, String hospital) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.specialty = specialty;
        this.image = image;
        this.rating = rating;
        this.reviews = reviews;
        this.experience = experience;
        this.availability = availability;
        this.fee = fee;
        this.bio = bio;
        this.nextSlot = nextSlot;
        this.hospital = hospital;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        this.reviews = reviews;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getNextSlot() {
        return nextSlot;
    }

    public void setNextSlot(String nextSlot) {
        this.nextSlot = nextSlot;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
}
