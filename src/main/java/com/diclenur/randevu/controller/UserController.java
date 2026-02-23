package com.diclenur.randevu.controller;

import com.diclenur.randevu.entity.User;
import com.diclenur.randevu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://127.0.0.1:3000", "http://127.0.0.1:8080"})
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    // Kullanıcı kaydı
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserRegistrationRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validasyonlar
            if (request.getFullName() == null || request.getFullName().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Ad Soyad alanı zorunludur.");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (request.getPhone() == null || request.getPhone().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Telefon alanı zorunludur.");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "E-posta alanı zorunludur.");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (request.getTcNo() == null || request.getTcNo().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "TC Kimlik No alanı zorunludur.");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Şifre alanı zorunludur.");
                return ResponseEntity.badRequest().body(response);
            }
            
            // TC Kimlik No 11 haneli kontrolü
            if (request.getTcNo().length() != 11) {
                response.put("success", false);
                response.put("message", "TC Kimlik No 11 haneli olmalıdır.");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Telefon numarası kontrolü
            if (request.getPhone().length() < 10) {
                response.put("success", false);
                response.put("message", "Telefon numarası en az 10 haneli olmalıdır.");
                return ResponseEntity.badRequest().body(response);
            }
            
            // E-posta format kontrolü
            if (!request.getEmail().contains("@") || !request.getEmail().contains(".")) {
                response.put("success", false);
                response.put("message", "Geçerli bir e-posta adresi giriniz.");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Şifre uzunluk kontrolü
            if (request.getPassword().length() < 6) {
                response.put("success", false);
                response.put("message", "Şifre en az 6 karakter olmalıdır.");
                return ResponseEntity.badRequest().body(response);
            }
            
            // E-posta benzersizlik kontrolü
            if (userRepository.existsByEmail(request.getEmail())) {
                response.put("success", false);
                response.put("message", "Bu e-posta adresi zaten kullanılmaktadır.");
                return ResponseEntity.badRequest().body(response);
            }
            
            // TC Kimlik No benzersizlik kontrolü
            if (userRepository.existsByTcNo(request.getTcNo())) {
                response.put("success", false);
                response.put("message", "Bu TC Kimlik No zaten kullanılmaktadır.");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Telefon numarası benzersizlik kontrolü
            if (userRepository.existsByPhone(request.getPhone())) {
                response.put("success", false);
                response.put("message", "Bu telefon numarası zaten kullanılmaktadır.");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Şifreyi hash'le
            String hashedPassword = passwordEncoder.encode(request.getPassword());
            
            // Yeni kullanıcı oluştur
            User newUser = new User(
                request.getFullName().trim(),
                request.getPhone().trim(),
                request.getEmail().trim().toLowerCase(),
                request.getTcNo().trim(),
                hashedPassword,
                request.getGender() != null ? request.getGender().trim() : null,
                request.getBirthDate(),
                request.getHeight(),
                request.getWeight()
            );
            
            User savedUser = userRepository.save(newUser);
            
            response.put("success", true);
            response.put("message", "Kayıt başarıyla tamamlandı!");
            response.put("userId", savedUser.getId());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Kayıt sırasında bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // Kullanıcı girişi (E-posta ile)
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserLoginRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<User> userOpt = userRepository.findByEmailAndIsActiveTrue(request.getEmail());
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                    response.put("success", true);
                    response.put("message", "Giriş başarılı!");
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("id", user.getId());
                    userInfo.put("fullName", user.getFullName());
                    userInfo.put("email", user.getEmail());
                    userInfo.put("tcNo", user.getTcNo());
                    userInfo.put("phone", user.getPhone());
                    userInfo.put("gender", user.getGender() != null ? user.getGender() : "");
                    userInfo.put("birthDate", user.getBirthDate() != null ? user.getBirthDate().toString() : "");
                    
                    response.put("user", userInfo);
                    return ResponseEntity.ok(response);
                } else {
                    response.put("success", false);
                    response.put("message", "Şifre hatalı!");
                    return ResponseEntity.badRequest().body(response);
                }
            } else {
                response.put("success", false);
                response.put("message", "Bu e-posta adresi ile kayıtlı kullanıcı bulunamadı!");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Giriş sırasında bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // Kullanıcı girişi (TC Kimlik No ile)
    @PostMapping("/login-tc")
    public ResponseEntity<Map<String, Object>> loginUserByTc(@RequestBody UserLoginByTcRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<User> userOpt = userRepository.findByTcNoAndIsActiveTrue(request.getTcNo());
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                    response.put("success", true);
                    response.put("message", "Giriş başarılı!");
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("id", user.getId());
                    userInfo.put("fullName", user.getFullName());
                    userInfo.put("email", user.getEmail());
                    userInfo.put("tcNo", user.getTcNo());
                    userInfo.put("phone", user.getPhone());
                    userInfo.put("gender", user.getGender() != null ? user.getGender() : "");
                    userInfo.put("birthDate", user.getBirthDate() != null ? user.getBirthDate().toString() : "");
                    
                    response.put("user", userInfo);
                    return ResponseEntity.ok(response);
                } else {
                    response.put("success", false);
                    response.put("message", "Şifre hatalı!");
                    return ResponseEntity.badRequest().body(response);
                }
            } else {
                response.put("success", false);
                response.put("message", "Bu TC Kimlik No ile kayıtlı kullanıcı bulunamadı!");
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Giriş sırasında bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // Tüm kullanıcıları listele (admin için) - GÜVENLİK: Bu endpoint kaldırıldı
    // Production'da bu endpoint'i kullanmayın veya proper authentication ekleyin
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        
        // GÜVENLİK UYARISI: Bu endpoint production'da kaldırılmalı
        response.put("success", false);
        response.put("message", "Bu endpoint güvenlik nedeniyle devre dışı bırakıldı");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
    
    // Kullanıcı bilgilerini güncelle
    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Kullanıcı bulunamadı!");
                return ResponseEntity.badRequest().body(response);
            }
            
            User user = userOpt.get();
            
            // Bilgileri güncelle
            if (request.getFullName() != null && !request.getFullName().trim().isEmpty()) {
                user.setFullName(request.getFullName().trim());
            }
            
            if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
                // Telefon benzersizlik kontrolü
                if (!user.getPhone().equals(request.getPhone().trim()) && 
                    userRepository.existsByPhone(request.getPhone().trim())) {
                    response.put("success", false);
                    response.put("message", "Bu telefon numarası zaten kullanılmaktadır.");
                    return ResponseEntity.badRequest().body(response);
                }
                user.setPhone(request.getPhone().trim());
            }
            
            if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
                // E-posta benzersizlik kontrolü
                if (!user.getEmail().equals(request.getEmail().trim().toLowerCase()) && 
                    userRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
                    response.put("success", false);
                    response.put("message", "Bu e-posta adresi zaten kullanılmaktadır.");
                    return ResponseEntity.badRequest().body(response);
                }
                user.setEmail(request.getEmail().trim().toLowerCase());
            }
            
            if (request.getGender() != null && !request.getGender().trim().isEmpty()) {
                user.setGender(request.getGender().trim());
            }
            
            if (request.getBirthDate() != null) {
                user.setBirthDate(request.getBirthDate());
            }
            
            if (request.getHeight() != null) {
                user.setHeight(request.getHeight());
            }
            
            if (request.getWeight() != null) {
                user.setWeight(request.getWeight());
            }
            
            if (request.getAddress() != null && !request.getAddress().trim().isEmpty()) {
                user.setAddress(request.getAddress().trim());
            }
            
            userRepository.save(user);
            
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("fullName", user.getFullName());
            userInfo.put("email", user.getEmail());
            userInfo.put("tcNo", user.getTcNo());
            userInfo.put("phone", user.getPhone());
            userInfo.put("gender", user.getGender() != null ? user.getGender() : "");
            userInfo.put("birthDate", user.getBirthDate() != null ? user.getBirthDate().toString() : "");
            userInfo.put("height", user.getHeight() != null ? user.getHeight() : "");
            userInfo.put("weight", user.getWeight() != null ? user.getWeight() : "");
            userInfo.put("address", user.getAddress() != null ? user.getAddress() : "");
            
            response.put("success", true);
            response.put("message", "Bilgileriniz başarıyla güncellendi!");
            response.put("user", userInfo);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Güncelleme sırasında bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // Şifre değiştir
    @PutMapping("/{userId}/password")
    public ResponseEntity<Map<String, Object>> changePassword(@PathVariable Long userId, @RequestBody PasswordChangeRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Kullanıcı bulunamadı!");
                return ResponseEntity.badRequest().body(response);
            }
            
            User user = userOpt.get();
            
            // Mevcut şifre kontrolü
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                response.put("success", false);
                response.put("message", "Mevcut şifre hatalı!");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Yeni şifre validasyonu
            if (request.getNewPassword() == null || request.getNewPassword().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "Yeni şifre alanı zorunludur!");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (request.getNewPassword().length() < 6) {
                response.put("success", false);
                response.put("message", "Yeni şifre en az 6 karakter olmalıdır!");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                response.put("success", false);
                response.put("message", "Yeni şifreler eşleşmiyor!");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Şifreyi hash'leyerek güncelle
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            
            response.put("success", true);
            response.put("message", "Şifreniz başarıyla değiştirildi!");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Şifre değiştirme sırasında bir hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // Kullanıcı bilgilerini getir
    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            
            if (!userOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "Kullanıcı bulunamadı!");
                return ResponseEntity.badRequest().body(response);
            }
            
            User user = userOpt.get();
            
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", user.getId());
            userInfo.put("fullName", user.getFullName());
            userInfo.put("email", user.getEmail());
            userInfo.put("tcNo", user.getTcNo());
            userInfo.put("phone", user.getPhone());
            userInfo.put("gender", user.getGender() != null ? user.getGender() : "");
            userInfo.put("birthDate", user.getBirthDate() != null ? user.getBirthDate().toString() : "");
            userInfo.put("height", user.getHeight() != null ? user.getHeight() : "");
            userInfo.put("weight", user.getWeight() != null ? user.getWeight() : "");
            userInfo.put("address", user.getAddress() != null ? user.getAddress() : "");
            userInfo.put("isActive", user.getIsActive());
            
            response.put("success", true);
            response.put("user", userInfo);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Kullanıcı bilgileri alınırken hata oluştu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // DTO sınıfları
    public static class UserRegistrationRequest {
        private String fullName;
        private String phone;
        private String email;
        private String tcNo;
        private String gender;
        private LocalDate birthDate;
        private Double height;
        private Double weight;
        private String password;
        
        // Getters and Setters
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getTcNo() { return tcNo; }
        public void setTcNo(String tcNo) { this.tcNo = tcNo; }
        
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
        
        public LocalDate getBirthDate() { return birthDate; }
        public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
        
        public Double getHeight() { return height; }
        public void setHeight(Double height) { this.height = height; }
        
        public Double getWeight() { return weight; }
        public void setWeight(Double weight) { this.weight = weight; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    public static class UserLoginRequest {
        private String email;
        private String password;
        
        // Getters and Setters
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    public static class UserLoginByTcRequest {
        private String tcNo;
        private String password;
        
        // Getters and Setters
        public String getTcNo() { return tcNo; }
        public void setTcNo(String tcNo) { this.tcNo = tcNo; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    public static class UserUpdateRequest {
        private String fullName;
        private String phone;
        private String email;
        private String gender;
        private LocalDate birthDate;
        private Double height;
        private Double weight;
        private String address;
        
        // Getters and Setters
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
        
        public LocalDate getBirthDate() { return birthDate; }
        public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
        
        public Double getHeight() { return height; }
        public void setHeight(Double height) { this.height = height; }
        
        public Double getWeight() { return weight; }
        public void setWeight(Double weight) { this.weight = weight; }
        
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }
    
    public static class PasswordChangeRequest {
        private String currentPassword;
        private String newPassword;
        private String confirmPassword;
        
        // Getters and Setters
        public String getCurrentPassword() { return currentPassword; }
        public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
        
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
        
        public String getConfirmPassword() { return confirmPassword; }
        public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
    }
}
