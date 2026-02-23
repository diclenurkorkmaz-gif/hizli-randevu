
        // Branş filtreleme fonksiyonu
        document.querySelectorAll('.specialty-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                // Tüm butonlardan active class'ını kaldır
                document.querySelectorAll('.specialty-btn').forEach(b => b.classList.remove('active'));
                // Tıklanan butona active class'ını ekle
                this.classList.add('active');
                
                const specialty = this.getAttribute('data-specialty');
                filterDoctors(specialty);
            });
        });

        function filterDoctors(specialty) {
            const doctorCards = document.querySelectorAll('.doctor-card');
            
            doctorCards.forEach(card => {
                if (specialty === 'all' || card.getAttribute('data-specialty') === specialty) {
                    card.style.display = 'block';
                    card.style.animation = 'slideInUp 0.5s ease-out';
                } else {
                    card.style.display = 'none';
                }
            });
        }

        // Randevu alma fonksiyonu
        function bookAppointment(doctorName) {
            // Doktor adını URL parametresi olarak gönder
            const encodedDoctorName = encodeURIComponent(doctorName);
            window.location.href = `randevu-al.html?doctor=${encodedDoctorName}`;
        }

        // Doktor seçimi fonksiyonu
        function selectDoctor(doctorCard, doctorName) {
            // Tüm doktor kartlarından seçili sınıfını kaldır
            document.querySelectorAll('.doctor-card').forEach(card => {
                card.classList.remove('selected');
            });
            
            // Seçilen doktor kartına seçili sınıfını ekle
            doctorCard.classList.add('selected');
            
            // Seçilen doktoru global değişkende sakla
            window.selectedDoctor = doctorName;
            
            console.log('Seçilen doktor:', doctorName);
        }

        // Sidebar kategori filtreleme fonksiyonu
        function filterByCategory(specialty) {
            // Tüm branş butonlarından active class'ını kaldır
            document.querySelectorAll('.specialty-btn').forEach(b => b.classList.remove('active'));
            
            // İlgili branş butonunu aktif yap
            const targetBtn = document.querySelector(`[data-specialty="${specialty}"]`);
            if (targetBtn) {
                targetBtn.classList.add('active');
            }
            
            // Doktorları filtrele
            filterDoctors(specialty);
        }

        // Sayfa yüklendiğinde animasyon
        document.addEventListener('DOMContentLoaded', function() {
            const cards = document.querySelectorAll('.doctor-card');
            cards.forEach((card, index) => {
                card.style.animationDelay = `${index * 0.1}s`;
                card.style.animation = 'slideInUp 0.6s ease-out forwards';
            });
        });

        // CSS animasyonu ekle
        const style = document.createElement('style');
        style.textContent = `
            @keyframes slideInUp {
                from {
                    opacity: 0;
                    transform: translateY(30px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }
        `;
        document.head.appendChild(style);

        // Çıkış yap fonksiyonu
        function logout() {
            // localStorage'ı temizle
            localStorage.removeItem('user');
            // Giriş sayfasına yönlendir
            window.location.href = 'giris.html';
        }

        // Hesabım sayfasına git
        function goToAccount() {
            const storedUser = localStorage.getItem('user');
            if (storedUser) {
                window.location.href = 'hesabim.html';
            } else {
                window.location.href = 'giris.html';
            }
        }