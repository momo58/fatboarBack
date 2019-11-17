package com.pfa.fatboar.FatboarBack.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    /*@GetMapping("/custom-login")
    public String loadLoginPage() {
        return "login";
    }*/

    @GetMapping("/home")
    public String loadHomePage() {
        return "home";
    }


}
