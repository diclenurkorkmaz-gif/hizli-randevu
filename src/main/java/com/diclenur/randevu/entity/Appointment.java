package com.diclenur.randevu.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    @Column(name = "patient_name", nullable = false)
    private String patientName;
    
    @Column(name = "patient_phone")
    private String patientPhone;
    
    @Column(name = "patient_email")
    private String patientEmail;
    
    @Column(name = "patient_tc")
    private String patientTc;
    
    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate; // Gün/Ay/Yıl
    
    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime; // Saat
    
    @Column(name = "status")
    private String status = "PENDING"; // PENDING, CONFIRMED, CANCELLED
    
    @Column(name = "complaint")
    private String complaint; // Şikayet/rahatsızlık açıklaması
    
    @Column(name = "appointment_for")
    private String appointmentFor; // Kendim için, Annem için, vs.
    
    @Column(name = "relative_name")
    private String relativeName; // Yakın kişi adı
    
    @Column(name = "relative_tc")
    private String relativeTc; // Yakın kişi TC
    
    @Column(name = "relative_phone")
    private String relativePhone; // Yakın kişi telefon
    
    @Column(name = "relative_email")
    private String relativeEmail; // Yakın kişi e-posta
    
    // Constructors
    public Appointment() {}
    
    public Appointment(Doctor doctor, String patientName, String patientPhone, String patientEmail, 
                      String patientTc, LocalDate appointmentDate, LocalTime appointmentTime, String complaint,
                      String appointmentFor, String relativeName, String relativeTc, String relativePhone, String relativeEmail) {
        this.doctor = doctor;
        this.patientName = patientName;
        this.patientPhone = patientPhone;
        this.patientEmail = patientEmail;
        this.patientTc = patientTc;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.complaint = complaint;
        this.appointmentFor = appointmentFor;
        this.relativeName = relativeName;
        this.relativeTc = relativeTc;
        this.relativePhone = relativePhone;
        this.relativeEmail = relativeEmail;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    
    public String getPatientPhone() { return patientPhone; }
    public void setPatientPhone(String patientPhone) { this.patientPhone = patientPhone; }
    
    public String getPatientEmail() { return patientEmail; }
    public void setPatientEmail(String patientEmail) { this.patientEmail = patientEmail; }
    
    public String getPatientTc() { return patientTc; }
    public void setPatientTc(String patientTc) { this.patientTc = patientTc; }
    
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }
    
    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getComplaint() { return complaint; }
    public void setComplaint(String complaint) { this.complaint = complaint; }
    
    public String getAppointmentFor() { return appointmentFor; }
    public void setAppointmentFor(String appointmentFor) { this.appointmentFor = appointmentFor; }
    
    public String getRelativeName() { return relativeName; }
    public void setRelativeName(String relativeName) { this.relativeName = relativeName; }
    
    public String getRelativeTc() { return relativeTc; }
    public void setRelativeTc(String relativeTc) { this.relativeTc = relativeTc; }
    
    public String getRelativePhone() { return relativePhone; }
    public void setRelativePhone(String relativePhone) { this.relativePhone = relativePhone; }
    
    public String getRelativeEmail() { return relativeEmail; }
    public void setRelativeEmail(String relativeEmail) { this.relativeEmail = relativeEmail; }
}
