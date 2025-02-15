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
    
    @Value("${url.expiration.time}")
    private int expirationTime;

    @Value("${url.default.limit}")
    private int defaultClickLimit;

    public String shortenUrl(String originalUrl, UUID userId, int clickLimit) {
        String shortUrl = generateShortUrl(originalUrl);
        
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(30); // Устанавливаем срок действия на 30 дней
        
        Url url = new Url(originalUrl, shortUrl, userId, expirationDate, clickLimit);
        
        urlRepository.save(url);
        
        return shortUrl;
    }
    
 // В UrlShortenerService.java
    public String getOriginalUrl(String shortUrl) {
        List<Url> urls = urlRepository.findByShortUrl(shortUrl);
        if (urls != null && !urls.isEmpty()) {
            Url url = urls.get(0);
            if (url.getClickCount() < url.getClickLimit() && url.getExpirationDate().isAfter(LocalDateTime.now())) {
                url.incrementClickCount();
                urlRepository.save(url);
                return url.getOriginalUrl();
            } else {
                // Уведомление пользователя, если ссылка заблокирована
                return null; // Ссылка недействительна
            }
        }
        return null; // Ссылка не найдена
    }

    public boolean deleteUrl(String shortUrl, UUID userId) {
        List<Url> urls = urlRepository.findByShortUrl(shortUrl);
        if (urls != null && !urls.isEmpty()) {
            Url url = urls.get(0);
            if (url.getUserId().equals(userId)) {
                urlRepository.delete(url);
                return true;
            }
        }
        return false;
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
