package com.utn2019.avanzada2.tp9.controller;

import static org.springframework.http.HttpStatus.TEMPORARY_REDIRECT;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<Void> home() {
        return ResponseEntity.status(TEMPORARY_REDIRECT)
                .header("Location", "swagger-ui.html")
                .build();
    }
}
