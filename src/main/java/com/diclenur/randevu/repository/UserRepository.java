package com.diclenur.randevu.repository;

import com.diclenur.randevu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Email ile kullanıcı bulma
    Optional<User> findByEmail(String email);
    
    // TC Kimlik No ile kullanıcı bulma
    Optional<User> findByTcNo(String tcNo);
    
    // Telefon ile kullanıcı bulma
    Optional<User> findByPhone(String phone);
    
    // Email'in var olup olmadığını kontrol etme
    boolean existsByEmail(String email);
    
    // TC Kimlik No'nun var olup olmadığını kontrol etme
    boolean existsByTcNo(String tcNo);
    
    // Telefon numarasının var olup olmadığını kontrol etme
    boolean existsByPhone(String phone);
    
    // Aktif kullanıcıları getirme
    Optional<User> findByEmailAndIsActiveTrue(String email);
    
    // TC Kimlik No ile aktif kullanıcı bulma
    Optional<User> findByTcNoAndIsActiveTrue(String tcNo);
}
