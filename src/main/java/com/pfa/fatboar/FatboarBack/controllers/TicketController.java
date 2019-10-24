package com.pfa.fatboar.FatboarBack.controllers;

import com.pfa.fatboar.FatboarBack.exception.AppException;
import com.pfa.fatboar.FatboarBack.models.Gain;
import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import com.pfa.fatboar.FatboarBack.security.CurrentUser;
import com.pfa.fatboar.FatboarBack.security.UserPrincipal;
import com.pfa.fatboar.FatboarBack.services.TicketService;
import com.pfa.fatboar.FatboarBack.services.UserService;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    public static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    /**
     * Processes the ticket number submission by the client on the site
     * @param ticket
     * @return
     * @throws Exception
     */
    @PostMapping("/handlingpost")
    public ResponseEntity<String> handlePostFormTicketSubmission(@RequestBody Ticket ticket, @CurrentUser UserPrincipal userPrincipal) throws Exception {
        String gain = "";
        try {
            gain = ticketService.handleTicketSubmission(ticket.getTicketNumber(),userPrincipal);
        }
        catch(Exception e) {
            e.getMessage();
        }
        return new ResponseEntity<String>(gain, HttpStatus.OK);
    }

    /**
     * Returns -for a user logged in- the history of all its gains validated by the restaurant's employee
     * NB: think about adding the similar gains and returning a map with key ( The number of times gain )
     * and value the label of the gain
     * @param userPrincipal
     * @return
     */
    @GetMapping("/gainhistory")
    public ResponseEntity<List<String>> getUserGains(@CurrentUser UserPrincipal userPrincipal) {
        List<String > gains = new ArrayList<>();
        User userLoggedIn = userService.loggedInUser(userPrincipal);

        if (userLoggedIn != null) {
            List<Ticket> tickets = userLoggedIn.getTickets();

            if (tickets.isEmpty()) {
                logger.info("There is no tickets");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            for (Ticket t : tickets) {
                if (t.getState() == 2) {
                    gains.add(t.getGain().getLabel());
                }
            }
            return new ResponseEntity<List<String>>(gains, HttpStatus.OK);
        }
        logger.info("There is no user");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

