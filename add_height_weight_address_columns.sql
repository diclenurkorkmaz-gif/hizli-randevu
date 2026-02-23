-- Users tablosuna boy, kilo ve adres kolonlarını ekle
ALTER TABLE users 
ADD COLUMN height DOUBLE,
ADD COLUMN weight DOUBLE,
ADD COLUMN address VARCHAR(500);

-- Mevcut veriler için varsayılan değerler (opsiyonel)
-- UPDATE users SET height = NULL, weight = NULL, address = NULL WHERE height IS NULL;


