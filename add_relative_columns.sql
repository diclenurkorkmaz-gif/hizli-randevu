-- Yakın kişi bilgileri için yeni kolonları ekle
USE randevu_db;

-- Mevcut appointments tablosuna yeni kolonları ekle
ALTER TABLE appointments 
ADD COLUMN appointment_for VARCHAR(50) COMMENT 'Randevu kimin için (self, mother, father, daughter, son)',
ADD COLUMN relative_name VARCHAR(255) COMMENT 'Yakın kişi adı',
ADD COLUMN relative_tc VARCHAR(11) COMMENT 'Yakın kişi TC Kimlik No',
ADD COLUMN relative_phone VARCHAR(20) COMMENT 'Yakın kişi telefon',
ADD COLUMN relative_email VARCHAR(255) COMMENT 'Yakın kişi e-posta';

-- Tablo yapısını kontrol et
DESCRIBE appointments;

