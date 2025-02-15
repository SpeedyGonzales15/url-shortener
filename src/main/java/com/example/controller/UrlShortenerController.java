package com.example.controller;

import com.example.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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

    @GetMapping("/{shortUrl}")
    public RedirectView redirectUrl(@PathVariable String shortUrl) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortUrl);

        if (originalUrl != null) {
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(originalUrl);
            return redirectView;
        } else {
            // Обработка случая, когда короткая ссылка не найдена
            return new RedirectView("/error"); // Или любой другой URL для обработки ошибки
        }
    }

    @DeleteMapping("/{shortUrl}")
    public ResponseEntity<String> deleteUrl(@PathVariable String shortUrl, @RequestParam UUID userId) {
        boolean isDeleted = urlShortenerService.deleteUrl(shortUrl, userId);

        if (isDeleted) {
            return ResponseEntity.ok("Ссылка успешно удалена.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Вы не имеете права удалять эту ссылку.");
        }
    }
}
