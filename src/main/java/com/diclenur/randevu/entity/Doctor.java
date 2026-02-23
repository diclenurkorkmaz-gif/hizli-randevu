package com.diclenur.randevu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "specialty", nullable = false)
    private String specialty;
    
    @Column(name = "experience_years")
    private Integer experienceYears;
    
    @Column(name = "university")
    private String university;
    
    @Column(name = "rating")
    private Double rating;
    
    @Column(name = "avatar")
    private String avatar;
    
    // Constructors
    public Doctor() {}
    
    public Doctor(String firstName, String lastName, String specialty, Integer experienceYears, String university, Double rating, String avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.experienceYears = experienceYears;
        this.university = university;
        this.rating = rating;
        this.avatar = avatar;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getFullName() { return firstName + " " + lastName; }
    
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    
    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }
    
    public String getUniversity() { return university; }
    public void setUniversity(String university) { this.university = university; }
    
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}
