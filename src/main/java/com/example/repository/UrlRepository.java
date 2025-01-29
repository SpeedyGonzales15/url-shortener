package com.example.repository;

import com.example.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    
    List<Url> findAllByExpirationDateBefore(LocalDateTime now);
    
    List<Url> findByShortUrl(String shortUrl);
}
