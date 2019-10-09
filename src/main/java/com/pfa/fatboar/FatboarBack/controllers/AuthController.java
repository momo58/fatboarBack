package com.pfa.fatboar.FatboarBack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/custom-login")
    public String loadLoginPage() {
        return "login";
    }

}
