package com.edu.iff.ccc.books_trade.controller.restapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1")
public class RestApiMainController {

    @GetMapping("/register")
    public ResponseEntity<String> getApiHome() {
        return ResponseEntity.ok("Página de Troca de Livros");
    }
}
