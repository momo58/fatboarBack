package com.pfa.fatboar.FatboarBack.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfa.fatboar.FatboarBack.exception.AppException;
import com.pfa.fatboar.FatboarBack.models.Client;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import com.pfa.fatboar.FatboarBack.security.CurrentUser;
import com.pfa.fatboar.FatboarBack.security.UserPrincipal;
import com.pfa.fatboar.FatboarBack.services.ClientService;
import com.pfa.fatboar.FatboarBack.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
    
    @Autowired
    ClientService clientService;

    @GetMapping("/home")
    public String loadHomePage() {
        return "home";
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    /**
     * In case which we want to show the user details
     * @param userPrincipal
     * @return
     */
    @GetMapping("/user/me")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
       return userService.loggedInUser(userPrincipal);
    }
    
    @PostMapping("/user/toggleSubscribe")
    public ResponseEntity<Boolean> toggleSubscribe(@CurrentUser UserPrincipal userPrincipal, 
    		@RequestBody Client subscriber) {
    	return ResponseEntity
    			.status(HttpStatus.OK)
    			.body(clientService.toggleSubscribe(userPrincipal, subscriber.isSubscribeToNewsLetters()));
    }

    /**
     * Returns the user who win for the Evoque
     * NB: to correct with only users with rolename client and I have to config the detail when I save a new G/FB user
     * @return
     */
    @GetMapping("youwin")
    public ResponseEntity<?> youWin() {
        try {
            User theWinner = userService.getTheUserWhoWin();
            return ResponseEntity.status(HttpStatus.OK).body(theWinner);
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }
}
