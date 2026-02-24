# 🏥 Hızlı Randevu - Online Randevu Yönetim Sistemi

**Modern, güvenli ve kullanıcı dostu randevu yönetim platformu**

Hızlı Randevu, hastaların kolayca randevu alabilmesi ve doktorların randevularını yönetebilmesi için geliştirilmiş modern bir web uygulamasıdır. Spring Boot teknolojisi ile geliştirilmiş, güvenli ve ölçeklenebilir bir sistem.

## ✨ Temel Özellikler

### 👥 Kullanıcı Yönetimi
- **Güvenli Kayıt**: E-posta veya TC Kimlik No ile hızlı kayıt
- **Çoklu Giriş**: E-posta veya TC Kimlik No ile esnek giriş seçenekleri
- **Profil Yönetimi**: Kişisel bilgileri güncelleme ve şifre değiştirme
- **Güvenli Kimlik Doğrulama**: BCrypt ile şifre hashleme

### 🩺 Doktor Yönetimi
- **Doktor Kaydı**: Detaylı doktor bilgi sistemi
- **Uzmanlık Alanları**: Doktor uzmanlık kategorileri
- **Çalışma Saatleri**: Esnek çalışma saatleri yönetimi
- **Doktor Profilleri**: Detaylı doktor bilgi sayfaları

### 📅 Randevu Sistemi
- **Online Randevu**: 7/24 randevu alma imkanı
- **Randevu Takibi**: Geçmiş ve gelecek randevuları görüntüleme
- **Randevu Yönetimi**: Randevu iptal etme ve değiştirme
- **Otomatik Bildirimler**: Randevu hatırlatmaları

### 🔒 Güvenlik Özellikleri
- **Şifre Güvenliği**: BCrypt ile güvenli şifre hashleme
- **CORS Koruması**: Sınırlı origin erişimi
- **Input Validation**: Kapsamlı veri doğrulama
- **SQL Injection Koruması**: JPA ile güvenli veritabanı erişimi

## 🛠️ Teknolojiler

- **Backend**: Spring Boot 3.5.5
- **Database**: MySQL
- **ORM**: Spring Data JPA
- **Template Engine**: Thymeleaf
- **Security**: Spring Security Crypto
- **Build Tool**: Maven

## 📋 Gereksinimler

- Java 17+
- MySQL 8.0+
- Maven 3.6+

## ⚙️ Kurulum

### 1. Projeyi Klonlayın
```bash
git clone https://github.com/Diclenurkorkmaz/hizli-randevu.git
cd hizli-randevu/randevu
```

### 2. Veritabanı Ayarları
MySQL'de yeni bir veritabanı oluşturun:
```sql
CREATE DATABASE randevu_db;
```

### 3. Environment Variables
`application.properties` dosyasını oluşturun ve aşağıdaki bilgileri girin:

```properties
spring.application.name=randevu
server.port=8084

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/randevu_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=false
```

### 4. Uygulamayı Çalıştırın
```bash
mvn clean install
mvn spring-boot:run
```

Uygulama `http://localhost:8084` adresinde çalışacaktır.

## 📚 API Endpoints

### Kullanıcı İşlemleri
- `POST /api/users/register` - Kullanıcı kaydı
- `POST /api/users/login` - E-posta ile giriş
- `POST /api/users/login-tc` - TC Kimlik No ile giriş
- `GET /api/users/{id}` - Kullanıcı bilgileri
- `PUT /api/users/{id}` - Kullanıcı bilgilerini güncelle
- `PUT /api/users/{id}/password` - Şifre değiştir

### Doktor İşlemleri
- `POST /api/doctors/register` - Doktor kaydı
- `GET /api/doctors` - Tüm doktorları listele
- `GET /api/doctors/{id}` - Doktor bilgileri
- `PUT /api/doctors/{id}` - Doktor bilgilerini güncelle

### Randevu İşlemleri
- `POST /api/appointments` - Randevu oluştur
- `GET /api/appointments/user/{userId}` - Kullanıcının randevuları
- `GET /api/appointments/doctor/{doctorId}` - Doktorun randevuları
- `PUT /api/appointments/{id}` - Randevu güncelle
- `DELETE /api/appointments/{id}` - Randevu iptal et

## 🔒 Güvenlik

- Şifreler BCrypt ile hash'lenir
- CORS ayarları sınırlı origin'lere açık
- Hassas bilgiler environment variables ile yönetilir
- SQL injection koruması

## 📁 Proje Yapısı

```
src/
├── main/
│   ├── java/com/diclenur/randevu/
│   │   ├── controller/     # REST Controller'lar
│   │   ├── entity/         # JPA Entity'ler
│   │   ├── repository/     # Data Repository'ler
│   │   └── RandevuApplication.java
│   └── resources/
│       ├── static/         # Statik dosyalar (HTML, CSS, JS)
│       ├── templates/      # Thymeleaf template'ler
│       └── application.properties
└── test/                   # Test dosyaları
```

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Commit yapın (`git commit -m 'Add some amazing feature'`)
4. Push yapın (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun

## 📄 Lisans

Bu proje MIT lisansı altında lisanslanmıştır.

## 🎯 Proje Amacı

Hızlı Randevu, sağlık sektöründeki dijital dönüşüm ihtiyacından yola çıkarak geliştirilmiştir. Geleneksel telefon tabanlı randevu sistemlerinin aksine, modern web teknolojileri kullanarak:

- **Hastalar** için kolay ve hızlı randevu alma deneyimi
- **Doktorlar** için etkili randevu yönetimi
- **Sağlık kurumları** için dijital dönüşüm çözümü

## 🌟 Neden Hızlı Randevu?

- ⚡ **Hızlı ve Kolay**: Sadece birkaç tıklama ile randevu
- 🔒 **Güvenli**: Modern şifreleme ve güvenlik standartları
- 📱 **Responsive**: Tüm cihazlarda mükemmel deneyim
- 🚀 **Ölçeklenebilir**: Büyük sağlık kurumları için uygun
- 💰 **Maliyet Etkin**: Açık kaynak ve ücretsiz

## 📊 Hedef Kitle

- **Hastalar**: Online randevu almak isteyen bireyler
- **Doktorlar**: Randevu yönetimi yapan sağlık profesyonelleri
- **Sağlık Kurumları**: Dijital dönüşüm arayan hastaneler ve klinikler
- **Geliştiriciler**: Sağlık teknolojileri geliştiren yazılımcılar

## 🚀 Gelecek Planları

- [ ] **Mobil Uygulama**: iOS ve Android uygulamaları
- [ ] **SMS/Email Bildirimleri**: Otomatik randevu hatırlatmaları
- [ ] **Ödeme Entegrasyonu**: Online ödeme sistemi
- [ ] **Video Konsültasyon**: Uzaktan muayene özelliği
- [ ] **AI Asistan**: Akıllı randevu önerileri

## 👨‍💻 Geliştirici

**Dicle Nur Korkmaz** - Full Stack Developer
- 🐙 GitHub: [Diclenurkorkmaz](https://github.com/Diclenurkorkmaz)
- 💼 LinkedIn: [LinkedIn Profili]
- 📧 Email: [İletişim için GitHub üzerinden mesaj atın]

## 📞 Destek ve İletişim

- 🐛 **Bug Report**: [Issues](https://github.com/Diclenurkorkmaz/hizli-randevu/issues) sayfasından bildirin
- 💡 **Öneriler**: [Discussions](https://github.com/Diclenurkorkmaz/hizli-randevu/discussions) bölümünü kullanın
- 📧 **Genel Sorular**: GitHub üzerinden iletişime geçin

## 🤝 Katkıda Bulunma

Bu proje açık kaynak olarak geliştirilmektedir. Katkılarınızı bekliyoruz!

1. ⭐ **Star** vererek projeyi destekleyin
2. 🍴 **Fork** yaparak kendi versiyonunuzu oluşturun
3. 🔧 **Pull Request** göndererek katkıda bulunun
4. 📢 **Paylaşın** ve daha fazla kişiye ulaştırın

---

<div align="center">

**⭐ Bu projeyi beğendiyseniz star vermeyi unutmayın! ⭐**

Made with ❤️ by [Diclenur Korkmaz](https://github.com/diclenurkorkmaz-gif)

</div>

