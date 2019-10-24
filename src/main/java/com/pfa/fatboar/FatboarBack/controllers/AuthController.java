package com.pfa.fatboar.FatboarBack.controllers;

import com.pfa.fatboar.FatboarBack.exception.AppException;
import com.pfa.fatboar.FatboarBack.models.Role;
import com.pfa.fatboar.FatboarBack.models.RoleName;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.payload.ApiResponse;
import com.pfa.fatboar.FatboarBack.payload.AuthResponse;
import com.pfa.fatboar.FatboarBack.payload.LoginRequest;
import com.pfa.fatboar.FatboarBack.payload.SignupRequest;
import com.pfa.fatboar.FatboarBack.repositories.RoleRepository;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import com.pfa.fatboar.FatboarBack.utilities.JwtTokenUtil;
import com.pfa.fatboar.FatboarBack.utilities.TokenProvider;
import org.hibernate.mapping.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /*@GetMapping("/custom-login")
    public String loadLoginPage() {
        return "login";
    }*/



}
