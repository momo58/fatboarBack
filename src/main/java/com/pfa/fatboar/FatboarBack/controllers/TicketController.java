package com.pfa.fatboar.FatboarBack.controllers;

import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
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

    @PostMapping("/handling_tickets")
    public ResponseEntity<String> handleFormTicketSubmission(@ModelAttribute("ticket")Ticket ticket) throws Exception {
        try {
            int current = ticket.getTicketNumber();
            ticketService.handleTicketSubmission(current);
        }
        catch(Exception e) {
            e.getMessage();
        }
        return new ResponseEntity<>("Test return", HttpStatus.OK);
    }

    /**
     * Returns the tickets of the loggedin user
     * @return
     */
    @GetMapping("/yourtickets")
    public ResponseEntity<List<Ticket>> getUserTickets(Principal principal) {
        String sub = userService.getTheSubOfTheActualLoggedInUser(principal);
        logger.info("sub: ", sub);
        User userLoggedIn = userRepository.findBySub(sub);
        logger.info("user: ", userLoggedIn);
        if (userLoggedIn != null) {
            List<Ticket> tickets = userLoggedIn.getTickets();
            if (tickets.isEmpty()) {
                logger.info("There is no tickets");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
        }
        logger.info("There is no user");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
