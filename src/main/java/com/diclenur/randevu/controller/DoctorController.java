package com.diclenur.randevu.controller;

import com.diclenur.randevu.entity.Doctor;
import com.diclenur.randevu.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/api/doctors")
public class DoctorController {
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    // API Endpoints
    @GetMapping
    @ResponseBody
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    
    @GetMapping("/specialty/{specialty}")
    @ResponseBody
    public List<Doctor> getDoctorsBySpecialty(@PathVariable String specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }
    
    @PostMapping
    @ResponseBody
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }
    
    @PostMapping("/init")
    @ResponseBody
    public String initializeDoctors() {
        // HTML'deki doktorları veritabanına kaydet
        Doctor[] doctors = {
            new Doctor("Ahmet", "Yılmaz", "Dahiliye", 15, "İstanbul Üniversitesi", 4.8, "👨‍⚕️"),
            new Doctor("Fatma", "Demir", "Dahiliye", 12, "Hacettepe Üniversitesi", 4.9, "👩‍⚕️"),
            new Doctor("Mehmet", "Kaya", "Dahiliye", 18, "Ankara Üniversitesi", 4.7, "👨‍⚕️"),
            new Doctor("Ayşe", "Özkan", "Dahiliye", 10, "Ege Üniversitesi", 4.6, "👩‍⚕️"),
            new Doctor("Mustafa", "Çelik", "Kardiyoloji", 20, "İstanbul Üniversitesi", 4.9, "👨‍⚕️"),
            new Doctor("Zeynep", "Arslan", "Kardiyoloji", 14, "Hacettepe Üniversitesi", 4.8, "👩‍⚕️"),
            new Doctor("Emre", "Şahin", "Kardiyoloji", 16, "Ankara Üniversitesi", 4.7, "👨‍⚕️"),
            new Doctor("Sibel", "Yıldız", "Kardiyoloji", 11, "Ege Üniversitesi", 4.6, "👩‍⚕️"),
            new Doctor("Ali", "Veli", "Göz Hastalıkları", 13, "İstanbul Üniversitesi", 4.8, "👨‍⚕️"),
            new Doctor("Elif", "Korkmaz", "Göz Hastalıkları", 9, "Hacettepe Üniversitesi", 4.9, "👩‍⚕️"),
            new Doctor("Burak", "Öztürk", "Göz Hastalıkları", 17, "Ankara Üniversitesi", 4.7, "👨‍⚕️"),
            new Doctor("Gülay", "Aktaş", "Göz Hastalıkları", 12, "Ege Üniversitesi", 4.6, "👩‍⚕️"),
            new Doctor("Cem", "Yılmaz", "Ortopedi", 19, "İstanbul Üniversitesi", 4.8, "👨‍⚕️"),
            new Doctor("Deniz", "Özkan", "Ortopedi", 14, "Hacettepe Üniversitesi", 4.7, "👩‍⚕️"),
            new Doctor("Hakan", "Demir", "Nöroloji", 16, "Ankara Üniversitesi", 4.9, "👨‍⚕️"),
            new Doctor("İpek", "Kaya", "Nöroloji", 11, "Ege Üniversitesi", 4.6, "👩‍⚕️"),
            new Doctor("Murat", "Şahin", "Dermatoloji", 13, "İstanbul Üniversitesi", 4.8, "👨‍⚕️"),
            new Doctor("Özlem", "Arslan", "Dermatoloji", 10, "Hacettepe Üniversitesi", 4.7, "👩‍⚕️"),
            new Doctor("Serkan", "Yıldız", "Psikiyatri", 15, "Ankara Üniversitesi", 4.9, "👨‍⚕️"),
            new Doctor("Tuğba", "Öztürk", "Psikiyatri", 12, "Ege Üniversitesi", 4.6, "👩‍⚕️")
        };
        
        for (Doctor doctor : doctors) {
            doctorRepository.save(doctor);
        }
        
        return "Doktorlar başarıyla veritabanına kaydedildi!";
    }
}

// Ayrı bir Controller HTML sayfaları için
@Controller
class PageController {
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @GetMapping("/doktor")
    public String doktorSayfasi(Model model) {
        List<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("doctors", doctors);
        return "doktor";
    }
}