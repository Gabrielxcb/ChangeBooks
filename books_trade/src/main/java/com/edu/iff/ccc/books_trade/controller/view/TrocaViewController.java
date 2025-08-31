package com.edu.iff.ccc.books_trade.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/trocas")
public class TrocaViewController {

    @GetMapping
    public String listarTrocas(Model model) {
        return "trocas"; // trocas.html
    }

    @GetMapping("/nova")
    public String novaTrocaForm(Model model) {
        return "troca_form"; // troca_form.html
    }

    @GetMapping("/{id}")
    public String detalhesTroca(@PathVariable("id") Long id, Model model) {
        model.addAttribute("trocaId", id);
        return "troca"; // troca.html
    }
}

