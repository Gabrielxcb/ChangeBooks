package com.edu.iff.ccc.books_trade.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    @GetMapping
    public String painelAdmin(Model model) {
        return "admin";
    }

    @GetMapping("/usuarios")
    public String gerenciarUsuarios(Model model) {
        return "admin_usuarios";
    }

    @GetMapping("/livros")
    public String gerenciarLivros(Model model) {
        return "admin_livros";
    }

    @GetMapping("/trocas")
    public String gerenciarTrocas(Model model) {
        return "admin_trocas";
    }
}

