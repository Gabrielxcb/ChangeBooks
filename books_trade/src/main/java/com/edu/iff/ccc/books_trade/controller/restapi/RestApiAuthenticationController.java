package com.edu.iff.ccc.books_trade.controller.restapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/authentication")
public class RestApiAuthenticationController {

    @PostMapping("/register")
    public ResponseEntity<String> register() {
        return ResponseEntity.ok("Em produção");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Em produção");
    }
}
