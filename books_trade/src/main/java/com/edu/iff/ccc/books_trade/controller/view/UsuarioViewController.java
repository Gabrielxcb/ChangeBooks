package com.edu.iff.ccc.books_trade.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    @GetMapping("/{id}")
    public String getPerfilUsuario(@PathVariable("id") Long id, Model model) {
        model.addAttribute("usuarioId", id);
        return "usuario"; // usuario.html
    }

    @GetMapping("/{id}/editar")
    public String editarPerfilUsuario(@PathVariable("id") Long id, Model model) {
        model.addAttribute("usuarioId", id);
        return "usuario_form"; // usuario_form.html
    }
}

