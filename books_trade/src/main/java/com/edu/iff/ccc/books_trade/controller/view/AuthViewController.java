package com.edu.iff.ccc.books_trade.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthViewController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login"; // login.html
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register"; // register.html
    }
}

