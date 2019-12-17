package com.pfa.fatboar.FatboarBack.controllers;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfa.fatboar.FatboarBack.exception.AppException;
import com.pfa.fatboar.FatboarBack.models.Admin;
import com.pfa.fatboar.FatboarBack.models.Employe;
import com.pfa.fatboar.FatboarBack.models.Manager;
import com.pfa.fatboar.FatboarBack.models.Role;
import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.payload.AdminSignupRequest;
import com.pfa.fatboar.FatboarBack.payload.ApiResponse;
import com.pfa.fatboar.FatboarBack.payload.AuthResponse;
import com.pfa.fatboar.FatboarBack.payload.EmailingRequest;
import com.pfa.fatboar.FatboarBack.payload.GamePresentationRequest;
import com.pfa.fatboar.FatboarBack.payload.LoginRequest;
import com.pfa.fatboar.FatboarBack.repositories.AdminRepository;
import com.pfa.fatboar.FatboarBack.repositories.EmployeRepository;
import com.pfa.fatboar.FatboarBack.repositories.GameRepository;
import com.pfa.fatboar.FatboarBack.repositories.ManagerRepository;
import com.pfa.fatboar.FatboarBack.repositories.TicketRepository;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import com.pfa.fatboar.FatboarBack.security.CurrentUser;
import com.pfa.fatboar.FatboarBack.security.UserPrincipal;
import com.pfa.fatboar.FatboarBack.services.ClientService;
import com.pfa.fatboar.FatboarBack.services.TicketService;
import com.pfa.fatboar.FatboarBack.services.UserService;
import com.pfa.fatboar.FatboarBack.utilities.JwtTokenUtil;
import com.pfa.fatboar.FatboarBack.utils.PasswordUtils;

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
    
    @Autowired
    AdminRepository adminRepository;
    
    @Autowired
    ManagerRepository managerRepository;
    
    @Autowired
    EmployeRepository employeRepository;

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
    
    /* GESTION DES ADMINISTRATEURS */
    
    @GetMapping("admins")
    public ResponseEntity<?> getAllAdmins() {
    	return ResponseEntity.ok(adminRepository.findAll());
    }
    
    @PutMapping("admin")
    public ResponseEntity<?> createAdmin(@RequestBody AdminSignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email address already in use !"), HttpStatus.BAD_REQUEST);
        }

        Admin user = new Admin(signupRequest.getUsername(), signupRequest.getEmail(), signupRequest.getPassword(), signupRequest.getPerimetre());
        user.setClearPwd(PasswordUtils.getPassword(8));
        user.setPassword(passwordEncoder.encode(user.getClearPwd()));

        User result = userRepository.save(user);

        return ResponseEntity.ok(result);
    }
    
    @PostMapping("admin")
    public ResponseEntity<?> updateAdmin(@RequestBody Admin admin) {
    	return ResponseEntity.ok(adminRepository.save(admin));
    }
    
    @DeleteMapping("admin")
    public ResponseEntity<?> deleteAdmin(@RequestBody Admin admin) {
    	adminRepository.delete(admin);
    	return ResponseEntity.ok("L'administrateur a été supprimé");
    }
    
    /* GESTION DES MANAGERS */
    
    @GetMapping("managers")
    public ResponseEntity<List<Manager>> getAllManagers(@CurrentUser UserPrincipal userPrincipal) throws Exception {
    	Admin admin = adminRepository.findById(userPrincipal.getId()).orElseThrow(() -> new Exception("L'admin n'existe pas en base"));
    	return ResponseEntity.ok(managerRepository.findAllByPerimetre(admin.getPerimetre()));
    }
    
    @PutMapping("manager")
    public ResponseEntity<?> createManager(@RequestBody Manager manager, @CurrentUser UserPrincipal userPrincipal) throws Exception {
    	Admin admin = adminRepository.findById(userPrincipal.getId()).orElseThrow(() -> new Exception("L'admin n'existe pas en base"));
    	manager.setPerimetre(admin.getPerimetre());
    	manager.setClearPwd(PasswordUtils.getPassword(8));
    	manager.setPassword(passwordEncoder.encode(manager.getClearPwd()));
    	manager.setRole(manager.getRole());

        manager = managerRepository.save(manager);

        return ResponseEntity.ok(manager);
    }
    
    @PostMapping("manager")
    public ResponseEntity<?> updateManager(@RequestBody Manager manager) {
    	return ResponseEntity.ok(managerRepository.save(manager));
    }
    
    @DeleteMapping("manager")
    public ResponseEntity<?> deleteManager(@RequestBody Manager manager) {
    	managerRepository.delete(manager);
    	return ResponseEntity.ok("Le manager a été supprimé");
    }
    
    /* GESTION DES EMPLOYES */
    
    @GetMapping("employes")
    public ResponseEntity<?> getAllEmployes(@CurrentUser UserPrincipal userPrincipal) throws Exception {
    	Manager manager = managerRepository.findById(userPrincipal.getId()).orElseThrow(() -> new Exception("Le manager n'existe pas en base"));
    	return ResponseEntity.ok(employeRepository.findAllByRestaurant(manager.getRestaurant()));
    }
    
    @PutMapping("employe")
    public ResponseEntity<?> createEmploye(@RequestBody Employe employe, @CurrentUser UserPrincipal userPrincipal) throws Exception {
    	Manager manager = managerRepository.findById(userPrincipal.getId()).orElseThrow(() -> new Exception("Le manager n'existe pas en base"));
    	employe.setRestaurant(manager.getRestaurant());
    	employe.setClearPwd(PasswordUtils.getPassword(8));
    	employe.setPassword(passwordEncoder.encode(employe.getClearPwd()));
    	employe.setRole(employe.getRole());
    	employe = employeRepository.save(employe);

        return ResponseEntity.ok(employe);
    }
    
    @PostMapping("employe")
    public ResponseEntity<?> updateEmploye(@RequestBody Employe employe) {
    	return ResponseEntity.ok(employeRepository.save(employe));
    }
    
    @DeleteMapping("employe")
    public ResponseEntity<?> deleteEmploye(@RequestBody Employe employe) {
    	employeRepository.delete(employe);
    	return ResponseEntity.ok("L'employé a été supprimé");
    }
    
}
