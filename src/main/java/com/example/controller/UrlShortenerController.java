package com.example.controller;

import com.example.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestParam String originalUrl,
                                              @RequestParam UUID userId,
                                              @RequestParam(defaultValue = "100") int clickLimit) {
        
        String shortUrl = urlShortenerService.shortenUrl(originalUrl, userId, clickLimit);
        
        return ResponseEntity.ok(shortUrl);
    }
    
}
