package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Url {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String originalUrl;
    
    private String shortUrl;
    
    private UUID userId; // Идентификатор пользователя
    
    private LocalDateTime expirationDate; // Дата истечения срока действия
    
    private int clickLimit; // Лимит переходов
    
    private int clickCount; // Количество переходов

    // Конструкторы, геттеры и сеттеры

    public Url() {}

    public Url(String originalUrl, String shortUrl, UUID userId, LocalDateTime expirationDate, int clickLimit) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.userId = userId;
        this.expirationDate = expirationDate;
        this.clickLimit = clickLimit;
        this.clickCount = 0;
    }

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public int getClickLimit() {
        return clickLimit;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void incrementClickCount() {
        this.clickCount++;
    }
}
