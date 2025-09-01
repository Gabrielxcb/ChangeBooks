package com.edu.iff.ccc.books_trade.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.edu.iff.ccc.books_trade.entities.Troca;
import com.edu.iff.ccc.books_trade.entities.UsuarioComum;
import com.edu.iff.ccc.books_trade.entities.Livro;

@Controller
@RequestMapping("/trocas")
public class TrocaViewController {

    @GetMapping
    public String listarTrocas(Model model) {
        return "trocas"; // trocas.html
    }

    @GetMapping("/nova")
public String novaTrocaForm(Model model) {
    // 1. Cria o objeto principal para o formulário
    Troca troca = new Troca();
    
    // 2. Inicializa os objetos internos para evitar erro no th:field
    troca.setUsuario(new UsuarioComum());
    troca.setLivro(new Livro());     

    // 3. Adiciona o objeto ao model para o Thymeleaf poder usá-lo
    model.addAttribute("troca", troca);
    
    return "troca_form"; 
}

    @GetMapping("/{id}")
    public String detalhesTroca(@PathVariable("id") Long id, Model model) {
        model.addAttribute("trocaId", id);
        return "troca"; // troca.html
    }
}

