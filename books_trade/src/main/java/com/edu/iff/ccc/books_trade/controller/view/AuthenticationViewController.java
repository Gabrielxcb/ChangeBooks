package com.edu.iff.ccc.books_trade.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/authentication")
public class AuthenticationViewController {

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register.html";
    }

     @GetMapping("/login")
    public String getLoginPage() {
        return "login.html";
    }
}
