package com.example.service;

import com.example.model.Url;
import com.example.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UrlShortenerServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlShortenerService urlShortenerService;

    private String originalUrl;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        originalUrl = "http://example.com";
        userId = UUID.randomUUID();
    }

    @Test
    void testShortenUrl() {
        String shortUrl = urlShortenerService.shortenUrl(originalUrl, userId, 10);
        
        assertNotNull(shortUrl);
        assertTrue(shortUrl.startsWith("short.ly/"));
        
        // Verify that the URL is saved in the repository
        verify(urlRepository, times(1)).save(any(Url.class));
    }

    @Test
    void testDeleteExpiredUrls() {
        // This method will require additional setup for testing expiration logic.
        // You can mock the repository to return expired URLs and verify deletion.
        
        // Example of how you might implement this:
        // List<Url> expiredUrls = List.of(new Url(originalUrl, "short.ly/1", userId, LocalDateTime.now().minusDays(1), 10));
        // when(urlRepository.findAllByExpirationDateBefore(any())).thenReturn(expiredUrls);
        
        urlShortenerService.deleteExpiredUrls();
        
        // Verify that delete was called for each expired URL
        // verify(urlRepository, times(1)).delete(any(Url.class));
    }
}
