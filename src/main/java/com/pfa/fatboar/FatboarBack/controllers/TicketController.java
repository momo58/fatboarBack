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

import com.pfa.fatboar.FatboarBack.models.Client;
import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.payload.HistoryGainResponse;
import com.pfa.fatboar.FatboarBack.payload.InsertTicketsRequest;
import com.pfa.fatboar.FatboarBack.security.CurrentUser;
import com.pfa.fatboar.FatboarBack.security.UserPrincipal;
import com.pfa.fatboar.FatboarBack.services.ClientService;
import com.pfa.fatboar.FatboarBack.services.TicketService;
import com.pfa.fatboar.FatboarBack.services.UserService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    public static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    TicketService ticketService;
    
    @Autowired
    private ClientService clientService;

    @Autowired
    UserService userService;

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
        	return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<List<HistoryGainResponse>> getUserGains(@CurrentUser UserPrincipal userPrincipal) {
        Client userLoggedIn = clientService.loggedInClient(userPrincipal);
        return ResponseEntity.ok(clientService.findAllGains(userLoggedIn));
    }
    
    @PostMapping("/insertTickets")
    public ResponseEntity<String> batchInsertTickets(@RequestBody InsertTicketsRequest request) {
    	ticketService.batchInsertTickets(request);
    	return ResponseEntity.status(200).body("La tâche d'insertion des tickets a débutée");
    }
}

