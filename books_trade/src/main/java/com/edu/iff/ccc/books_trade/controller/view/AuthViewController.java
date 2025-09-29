package com.edu.iff.ccc.books_trade.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.edu.iff.ccc.books_trade.dto.UsuarioDTO;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/auth")
public class AuthViewController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login"; // login.html
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
    model.addAttribute("usuarioDTO", new UsuarioDTO());
    return "register";
}
}

