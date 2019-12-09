package com.pfa.fatboar.FatboarBack.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfa.fatboar.FatboarBack.models.Client;
import com.pfa.fatboar.FatboarBack.payload.ApiResponse;
import com.pfa.fatboar.FatboarBack.payload.SignupRequest;
import com.pfa.fatboar.FatboarBack.repositories.ClientRepository;

@RestController
@RequestMapping("/client")
public class ClientController {
	
	@Autowired
	private ClientRepository clientRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
        if (clientRepository.existsByEmail(signupRequest.getEmail())) {
            return new ResponseEntity("Cette adresse email est déjà utilisée", HttpStatus.BAD_REQUEST);
        }

        Client client = new Client(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword());
        client.setPassword(passwordEncoder.encode(client.getPassword()));

        clientRepository.save(client);

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }

}
