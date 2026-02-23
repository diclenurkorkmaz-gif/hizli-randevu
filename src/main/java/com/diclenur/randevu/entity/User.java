package com.diclenur.randevu.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;
    
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
    
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(name = "tc_no", nullable = false, unique = true, length = 11)
    private String tcNo;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "gender", length = 10)
    private String gender;
    
    @Column(name = "birth_date")
    private LocalDate birthDate;
    
    @Column(name = "height")
    private Double height;
    
    @Column(name = "weight")
    private Double weight;
    
    @Column(name = "address", length = 500)
    private String address;
    
    // Constructors
    public User() {}
    
    public User(String fullName, String phone, String email, String tcNo, String password) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.tcNo = tcNo;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public User(String fullName, String phone, String email, String tcNo, String password, String gender, LocalDate birthDate) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.tcNo = tcNo;
        this.password = password;
        this.gender = gender;
        this.birthDate = birthDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public User(String fullName, String phone, String email, String tcNo, String password, String gender, LocalDate birthDate, Double height, Double weight) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.tcNo = tcNo;
        this.password = password;
        this.gender = gender;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTcNo() {
        return tcNo;
    }
    
    public void setTcNo(String tcNo) {
        this.tcNo = tcNo;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    public Double getHeight() {
        return height;
    }
    
    public void setHeight(Double height) {
        this.height = height;
    }
    
    public Double getWeight() {
        return weight;
    }
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

