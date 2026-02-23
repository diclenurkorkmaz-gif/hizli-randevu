-- Users tablosu oluşturma
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    tc_no VARCHAR(11) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

-- Index'ler ekleme
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_tc_no ON users(tc_no);
CREATE INDEX idx_users_phone ON users(phone);

-- Örnek veri ekleme (isteğe bağlı)
INSERT INTO users (full_name, phone, email, tc_no, password) VALUES
('Ahmet Yılmaz', '05551234567', 'ahmet@example.com', '12345678901', 'password123'),
('Ayşe Demir', '05559876543', 'ayse@example.com', '98765432109', 'password456');
