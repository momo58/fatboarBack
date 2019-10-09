package com.pfa.fatboar.FatboarBack.services;

import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.repositories.TicketRepository;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    /**
     *
     * @param ticketNumber
     * @return Ticket
     * This method returns a ticket from a number passed as param
     */
    public Ticket getTicket(int ticketNumber) {
        return ticketRepository.findTicketByTicketNumber(ticketNumber);

    }

    /**
     *
     * @param number
     * @throws Exception
     * This method handle the ticket number submission
     */
    @Transactional
    public void handleTicketSubmission(int number) throws Exception {
        //Verifications
        Ticket ticket = getTicket(number);
        if (ticket == null) {
            throw new Exception("Ticket not found");
        } else {
            //Retrieve connected user and attach it to the ticket
            //String loggedInUsername = userService.getLoggedInUsername();
            //Just for test purposes --> to be deleted once done and uncomment line under
            String loggedInUsername = "Amira Khamassi";
            User userLoggedIn = userRepository.findByUsername(loggedInUsername);
            ticket.setUser(userLoggedIn.getId());
            //state 1 means that the ticket is well associated to a user and its gain is not yet consumed
            ticket.setState(1);
            ticketRepository.save(ticket);
        }
    }
}
