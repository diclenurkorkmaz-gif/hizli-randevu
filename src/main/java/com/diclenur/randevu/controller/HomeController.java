package com.diclenur.randevu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("redirect:/giris.html");
    }

    @GetMapping("/test")
    public String test() {
        return "MySQL bağlantısı başarılı! Veritabanı: randevu_db";
    }

    @GetMapping("/health")
    public String health() {
        return "Uygulama çalışıyor! ✅"; 
    }
}