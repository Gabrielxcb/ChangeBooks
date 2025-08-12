package com.edu.iff.ccc.books_trade.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/principal")
public class MainViewController {

    @GetMapping("/{id}")
    public String getHomePage(@PathVariable("id") String id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("titulo", "Rainha Vermelha");
        model.addAttribute("autor", "Victoria Aveyard");
        model.addAttribute("genero", "Fantasia");
        model.addAttribute("estadoConservacao", "Usado");
        model.addAttribute("disponivel", "Não");
        model.addAttribute("dono", "Joana");
        
        return "index.html";
    }
    @PostMapping("/register")
    public ResponseEntity<String> register() {
        return ResponseEntity.ok("Em produção");
    }
}
