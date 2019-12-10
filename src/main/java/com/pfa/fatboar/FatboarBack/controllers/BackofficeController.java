package com.pfa.fatboar.FatboarBack.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pfa.fatboar.FatboarBack.exception.AppException;
import com.pfa.fatboar.FatboarBack.models.Employe;
import com.pfa.fatboar.FatboarBack.models.Game;
import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.payload.ApiResponse;
import com.pfa.fatboar.FatboarBack.payload.AuthResponse;
import com.pfa.fatboar.FatboarBack.payload.EmailingRequest;
import com.pfa.fatboar.FatboarBack.payload.EmployeSignUpRequest;
import com.pfa.fatboar.FatboarBack.payload.GamePresentationRequest;
import com.pfa.fatboar.FatboarBack.payload.LoginRequest;
import com.pfa.fatboar.FatboarBack.repositories.GameRepository;
import com.pfa.fatboar.FatboarBack.repositories.TicketRepository;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import com.pfa.fatboar.FatboarBack.security.CurrentUser;
import com.pfa.fatboar.FatboarBack.security.UserPrincipal;
import com.pfa.fatboar.FatboarBack.services.ClientService;
import com.pfa.fatboar.FatboarBack.services.TicketService;
import com.pfa.fatboar.FatboarBack.services.UserService;
import com.pfa.fatboar.FatboarBack.utilities.JwtTokenUtil;

@RestController
@RequestMapping("/admin")
public class BackofficeController {

    public static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;
    
    @Autowired
    private ClientService clientService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    GameRepository gameRepository;

    @Autowired
    TicketRepository ticketRepository;

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/hello2")
    public String hello2(@RequestBody String name) {
        return "hello" + name;
    }

    @GetMapping("/apilog")
    public void testAPi() {
        logger.info("this is an info msg");
    }

    /**
     * Accessible only by Admin
     * It allows admins to manage the home page presentation
     */
    @PostMapping("/introducethegame")
    public ResponseEntity<?> introduceTheGame(@RequestBody GamePresentationRequest gamePresentationRequest) {
    	if (gamePresentationRequest.getContent() == null)
    		return new ResponseEntity(new ApiResponse(true, "Game presentation not successfully saved"), HttpStatus.BAD_REQUEST); 

    	gameRepository.findById(1L).ifPresent(game -> {
        	game.setContent(gamePresentationRequest.getContent());
        	gameRepository.save(game);
    	});
    	
    	return new ResponseEntity(new ApiResponse(true, "Game presentation successfully saved"), HttpStatus.OK);
    	
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerEmploye(@Valid @RequestBody EmployeSignUpRequest signupRequest) {
        logger.info("enter");
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email address already in use !"), HttpStatus.BAD_REQUEST);
        }

        User user = new Employe(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("api/user/me")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenUtil.generateToken(loginRequest.getEmail());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    /**
     * Permettre aux admin de visualiser les statistiques du jeu
     *
     * Selon le type des données récoltées:
     *      - le taux de participation au jeu est défini par le nombre de ticket saisis sur le site par un client
     *        sur le nombre total des tickets
     *      -
     */
    @GetMapping("/clientparticipationstat")
    public ResponseEntity<String> clientParticipationStatistics() {
        try {
            String percentage = ticketService.getclientParticipationStat();
            return ResponseEntity.status(HttpStatus.OK).body(percentage);
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    /**
     * Returns the emails list
     * @return
     */
    @GetMapping("/emails")
    public ResponseEntity<?> getUsersEmails() {
        List<String> emails = userService.getUsersEmails();

        if (emails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(emails);
    }

    /**
     * Sends emails NB: voir l'histoire de serveur SMTP
     * @param emailingRequest
     * @return
     */
    @PostMapping("send-email")
    public ResponseEntity<?> sendEmails(@RequestBody EmailingRequest emailingRequest) {

        SimpleMailMessage mail = new SimpleMailMessage();
        //List<String> emails = userService.getUsersEmails();
        List<String> emails = emailingRequest.getEmails();
        String[] arrayOfEmails = emails.toArray(new String[emails.size()]);


        try {
            for (int i = 0; i < arrayOfEmails.length; i++) {
                mail.setTo(arrayOfEmails[i]);
                mail.setSubject(emailingRequest.getSubject());
                mail.setText(emailingRequest.getContent());
                javaMailSender.send(mail);
            }

        } catch (MailException mailException) {
            mailException.getMessage();
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email not sent");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Email sent");
    }

    /**
     * Permettre aux employe du resto de noter un gain comme reçu
     */
    @PostMapping("validategain")
    public ResponseEntity<?> validateGainByEmployee(@RequestBody Ticket ticket, @CurrentUser UserPrincipal userPrincipal) throws Exception {
       boolean res = ticketService.handleTicketSubmissionByEmployee(ticket.getTicketNumber(), userPrincipal);
       if (true) {
           return new ResponseEntity(new ApiResponse(true, "Gain approved"), HttpStatus.OK);
       }
       return new ResponseEntity(new ApiResponse(false, "Gain not approved"), HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping("getContactList")
    public ResponseEntity<Resource> getContactListToCsvFormat() throws IOException {
    	File file = clientService.getAllSubscribedClientsToCsv();
    	
    	Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
        		.header("Content-Disposition" , "attachment; filename='" + file.getName() + "'; filename*='" + file.getName() + "'")
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
    
}
