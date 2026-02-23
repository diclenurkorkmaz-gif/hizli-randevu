package com.diclenur.randevu.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diclenur.randevu.entity.Appointment;
import com.diclenur.randevu.entity.Doctor;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByAppointmentDateBetween(LocalDate start, LocalDate end);
    List<Appointment> findByStatus(String status);
    List<Appointment> findByAppointmentDateAndAppointmentTime(LocalDate date, LocalTime time);
    List<Appointment> findByPatientNameContainingIgnoreCase(String patientName);
    List<Appointment> findByPatientPhone(String patientPhone);
    List<Appointment> findByPatientEmail(String patientEmail);
    List<Appointment> findByPatientTc(String patientTc);
    
    // Belirli doktor için belirli tarih ve saatte randevu var mı kontrol et
    List<Appointment> findByDoctorAndAppointmentDateAndAppointmentTime(Doctor doctor, LocalDate date, LocalTime time);
    
    // Belirli doktor için belirli tarihteki tüm randevuları getir
    List<Appointment> findByDoctorAndAppointmentDate(Doctor doctor, LocalDate date);
    
    // Belirli doktor için belirli tarih ve saatte randevu sayısını getir
    long countByDoctorAndAppointmentDateAndAppointmentTime(Doctor doctor, LocalDate date, LocalTime time);
    
    // Belirli hasta için yakınlarına alınan randevuları getir (kendisi için olmayan)
    List<Appointment> findByPatientTcAndAppointmentForNot(String relativeTc, String appointmentFor);
}