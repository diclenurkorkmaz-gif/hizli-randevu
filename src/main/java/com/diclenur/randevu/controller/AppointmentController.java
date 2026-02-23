package com.diclenur.randevu.controller;

import com.diclenur.randevu.entity.Appointment;
import com.diclenur.randevu.entity.Doctor;
import com.diclenur.randevu.repository.AppointmentRepository;
import com.diclenur.randevu.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    // Tüm randevuları getir
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    // Belirli bir doktorun randevularını getir
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor != null) {
            return appointmentRepository.findByDoctor(doctor);
        }
        return List.of();
    }
    
    // Belirli bir hastanın randevularını getir (TC kimlik numarasına göre)
    @GetMapping("/patient/{patientTc}")
    public List<Appointment> getAppointmentsByPatient(@PathVariable String patientTc) {
        return appointmentRepository.findByPatientTc(patientTc);
    }
    
    // Belirli bir hastanın yakınları için alınan randevuları getir
    @GetMapping("/relatives/{patientTc}")
    public List<Appointment> getRelativesAppointments(@PathVariable String patientTc) {
        return appointmentRepository.findByPatientTcAndAppointmentForNot(patientTc, "self");
    }
    
    // Belirli doktor için belirli tarihteki müsait saatleri getir
    @GetMapping("/available-times/{doctorId}/{date}")
    public List<String> getAvailableTimes(@PathVariable Long doctorId, @PathVariable String date) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor == null) {
            return List.of();
        }
        
        LocalDate appointmentDate = LocalDate.parse(date);
        List<Appointment> existingAppointments = appointmentRepository.findByDoctorAndAppointmentDate(doctor, appointmentDate);
        
        // Müsait saatler listesi
        List<String> allTimes = List.of(
            "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"
        );
        
        // Sadece aktif (iptal edilmemiş) randevuları filtrele
        List<String> bookedTimes = existingAppointments.stream()
            .filter(appointment -> !"CANCELLED".equals(appointment.getStatus()))
            .map(appointment -> appointment.getAppointmentTime().toString())
            .toList();
        
        return allTimes.stream()
            .filter(time -> !bookedTimes.contains(time))
            .toList();
    }
    
    // Belirli doktor için belirli tarihteki tüm saatleri (müsait ve dolu) getir
    @GetMapping("/all-times/{doctorId}/{date}")
    public List<TimeSlotInfo> getAllTimes(@PathVariable Long doctorId, @PathVariable String date) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (doctor == null) {
            return List.of();
        }
        
        LocalDate appointmentDate = LocalDate.parse(date);
        List<Appointment> existingAppointments = appointmentRepository.findByDoctorAndAppointmentDate(doctor, appointmentDate);
        
        // Tüm saatler listesi
        List<String> allTimes = List.of(
            "09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "14:00", "14:30", "15:00", "15:30", "16:00", "16:30"
        );
        
        // Sadece aktif (iptal edilmemiş) randevuları filtrele
        List<String> bookedTimes = existingAppointments.stream()
            .filter(appointment -> !"CANCELLED".equals(appointment.getStatus()))
            .map(appointment -> appointment.getAppointmentTime().toString())
            .toList();
        
        return allTimes.stream()
            .map(time -> new TimeSlotInfo(time, !bookedTimes.contains(time)))
            .toList();
    }
    
    // Yeni randevu oluştur
    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentRequest request) {
        Doctor doctor = doctorRepository.findById(request.getDoctorId()).orElse(null);
        if (doctor == null) {
            throw new RuntimeException("Doktor bulunamadı!");
        }
        
        // Randevu çakışması kontrolü
        LocalDate appointmentDate = LocalDate.parse(request.getAppointmentDate());
        LocalTime appointmentTime = LocalTime.parse(request.getAppointmentTime());
        
        // Sadece aktif (iptal edilmemiş) randevuları kontrol et
        List<Appointment> existingAppointments = appointmentRepository.findByDoctorAndAppointmentDate(doctor, appointmentDate);
        long activeAppointmentCount = existingAppointments.stream()
            .filter(appointment -> appointment.getAppointmentTime().equals(appointmentTime))
            .filter(appointment -> !"CANCELLED".equals(appointment.getStatus()))
            .count();
        
        if (activeAppointmentCount > 0) {
            throw new RuntimeException("Bu randevu saati dolu. Lütfen başka bir saat seçiniz.");
        }
        
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatientName(request.getPatientName());
        appointment.setPatientPhone(request.getPatientPhone());
        appointment.setPatientEmail(request.getPatientEmail());
        appointment.setPatientTc(request.getPatientTc());
        appointment.setAppointmentDate(appointmentDate);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setComplaint(request.getComplaint());
        appointment.setAppointmentFor(request.getAppointmentFor());
        appointment.setRelativeName(request.getRelativeName());
        appointment.setRelativeTc(request.getRelativeTc());
        appointment.setRelativePhone(request.getRelativePhone());
        appointment.setRelativeEmail(request.getRelativeEmail());
        appointment.setStatus("PENDING");
        
        return appointmentRepository.save(appointment);
    }
    
    // Randevu durumunu güncelle
    @PutMapping("/{id}/status")
    public Appointment updateAppointmentStatus(@PathVariable Long id, @RequestParam String status) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment != null) {
            appointment.setStatus(status);
            return appointmentRepository.save(appointment);
        }
        return null;
    }
    
    // Randevu sil
    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
    }
    
    // Geçmiş randevuları otomatik olarak tamamlandı olarak işaretle
    @PostMapping("/update-past-appointments")
    public String updatePastAppointments() {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();
        
        System.out.println("Şu anki zaman: " + now);
        System.out.println("Bugün: " + today);
        System.out.println("Şu anki saat: " + currentTime);
        
        // Tüm randevuları al (sadece upcoming değil)
        List<Appointment> allAppointments = appointmentRepository.findAll();
        System.out.println("Toplam randevu sayısı: " + allAppointments.size());
        
        int updatedCount = 0;
        
        for (Appointment appointment : allAppointments) {
            LocalDate appointmentDate = appointment.getAppointmentDate();
            LocalTime appointmentTime = appointment.getAppointmentTime();
            
            System.out.println("Randevu ID: " + appointment.getId() + 
                             ", Tarih: " + appointmentDate + 
                             ", Saat: " + appointmentTime + 
                             ", Durum: " + appointment.getStatus());
            
            // Sadece "upcoming" veya "past" durumundaki randevuları kontrol et
            if (("upcoming".equals(appointment.getStatus()) || "past".equals(appointment.getStatus())) &&
                (appointmentDate.isBefore(today) || 
                 (appointmentDate.isEqual(today) && appointmentTime.isBefore(currentTime)))) {
                
                System.out.println("Randevu güncelleniyor: " + appointment.getId() + " -> completed");
                appointment.setStatus("completed");
                appointmentRepository.save(appointment);
                updatedCount++;
            }
        }
        
        return "Geçmiş " + updatedCount + " randevu tamamlandı olarak işaretlendi.";
    }
    
    // Randevu isteği için DTO sınıfı
    public static class AppointmentRequest {
        private Long doctorId;
        private String patientName;
        private String patientPhone;
        private String patientEmail;
        private String patientTc;
        private String appointmentDate; // YYYY-MM-DD formatında
        private String appointmentTime; // HH:MM formatında
        private String complaint;
        private String appointmentFor; // Kendim için, Annem için, vs.
        private String relativeName; // Yakın kişi adı
        private String relativeTc; // Yakın kişi TC
        private String relativePhone; // Yakın kişi telefon
        private String relativeEmail; // Yakın kişi e-posta
        
        // Getters and Setters
        public Long getDoctorId() { return doctorId; }
        public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }
        
        public String getPatientName() { return patientName; }
        public void setPatientName(String patientName) { this.patientName = patientName; }
        
        public String getPatientPhone() { return patientPhone; }
        public void setPatientPhone(String patientPhone) { this.patientPhone = patientPhone; }
        
        public String getPatientEmail() { return patientEmail; }
        public void setPatientEmail(String patientEmail) { this.patientEmail = patientEmail; }
        
        public String getPatientTc() { return patientTc; }
        public void setPatientTc(String patientTc) { this.patientTc = patientTc; }
        
        public String getAppointmentDate() { return appointmentDate; }
        public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }
        
        public String getAppointmentTime() { return appointmentTime; }
        public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }
        
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
    
    // Saat slot bilgisi için DTO sınıfı
    public static class TimeSlotInfo {
        private String time;
        private boolean available;
        
        public TimeSlotInfo() {}
        
        public TimeSlotInfo(String time, boolean available) {
            this.time = time;
            this.available = available;
        }
        
        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }
        
        public boolean isAvailable() { return available; }
        public void setAvailable(boolean available) { this.available = available; }
    }
}