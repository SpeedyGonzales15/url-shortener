package com.example.service;

import com.example.model.Url;
import com.example.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlRepository urlRepository;

    public String shortenUrl(String originalUrl, UUID userId, int clickLimit) {
        String shortUrl = generateShortUrl(originalUrl);
        
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(30); // Устанавливаем срок действия на 30 дней
        
        Url url = new Url(originalUrl, shortUrl, userId, expirationDate, clickLimit);
        
        urlRepository.save(url);
        
        return shortUrl;
    }

    private String generateShortUrl(String originalUrl) {
        // Логика генерации короткой ссылки (например, с использованием Base62).
        return "short.ly/" + Math.abs(originalUrl.hashCode());
    }

    @Scheduled(fixedRate = 86400000) // Каждые 24 часа
    public void deleteExpiredUrls() {
        LocalDateTime now = LocalDateTime.now();
        List<Url> expiredUrls = urlRepository.findAllByExpirationDateBefore(now);
        
        for (Url url : expiredUrls) {
            urlRepository.delete(url);
        }
    }
}
