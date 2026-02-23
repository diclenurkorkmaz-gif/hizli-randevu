package com.diclenur.randevu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hızlı Randevu - Online Randevu Yönetim Sistemi
 * 
 * Modern, güvenli ve kullanıcı dostu randevu yönetim platformu.
 * Hastaların kolayca randevu alabilmesi ve doktorların randevularını 
 * yönetebilmesi için geliştirilmiş Spring Boot uygulaması.
 * 
 * @author Dicle Nur Korkmaz
 * @version 1.0.0
 * @since 2024
 */
@SpringBootApplication
public class RandevuApplication {

	/**
	 * Hızlı Randevu uygulamasının ana giriş noktası
	 * 
	 * @param args Komut satırı argümanları
	 */
	public static void main(String[] args) {
		SpringApplication.run(RandevuApplication.class, args);
	}

}
