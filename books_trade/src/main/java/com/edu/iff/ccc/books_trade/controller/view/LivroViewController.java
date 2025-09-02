package com.edu.iff.ccc.books_trade.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/livros")
public class LivroViewController {

    @GetMapping
    public String listarLivros(Model model) {
        return "livros"; // livros.html
    }

    @GetMapping("/novo")
    public String novoLivroForm(Model model) {
        return "livro_form"; // livro_form.html
    }

    @GetMapping("/{id}")
    public String detalhesLivro(@PathVariable("id") Long id, Model model) {
        model.addAttribute("livroId", id);
        return "livro"; // livro.html
    }
}

