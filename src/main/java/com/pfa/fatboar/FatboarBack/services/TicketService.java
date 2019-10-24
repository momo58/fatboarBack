package com.pfa.fatboar.FatboarBack.services;

import com.pfa.fatboar.FatboarBack.exception.AppException;
import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.payload.ApiResponse;
import com.pfa.fatboar.FatboarBack.repositories.TicketRepository;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import com.pfa.fatboar.FatboarBack.security.CurrentUser;
import com.pfa.fatboar.FatboarBack.security.UserPrincipal;
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

    public Ticket insertTickets(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    /**
     *
     * @param number
     * @throws Exception
     * This method handle the ticket number submission
     */
    @Transactional
    public String handleTicketSubmission(int number, @CurrentUser UserPrincipal userPrincipal) throws Exception {
        //Verifications
        Ticket ticket = getTicket(number);
        if (ticket == null) {
            throw new AppException("The ticket you have introduce is not found ! Try again. ");
        } else {
            User userLoggedIn = userService.loggedInUser(userPrincipal);
            ticket.setUser(userLoggedIn.getId());
            //state 1 means that the ticket is well associated to a user and its gain is not yet consumed
            ticket.setState(1);
            ticketRepository.save(ticket);

            return ticket.getGain().getLabel();
        }
    }

    /**
     *
     * @param number
     * @throws Exception
     * This method handle the ticket number submission by the employee
     */
    @Transactional
    public boolean handleTicketSubmissionByEmployee(int number, @CurrentUser UserPrincipal userPrincipal) throws Exception {

        Ticket ticket = getTicket(number);
        if (ticket == null) {
            throw new AppException("Employee: The ticket you have introduce is not found ! Try again. ");
        } else {
           /* User userTicketOwner = userRepository.findById(ticket.getUser())
                    .orElseThrow(() -> new AppException("User not found !"));*/

            //state 2 means that the ticket is well validated by the restaurant employee
            ticket.setState(2);
            ticketRepository.save(ticket);
            return true;
        }
    }

    /**
     * Returns tickets associated to a client
     */
    public String getclientParticipationStat() {

        int numberOfTicketsAssociatedToaClient = ticketRepository.countByState(1);
        long numberOfAllTickets = ticketRepository.count();

        long percentage = numberOfTicketsAssociatedToaClient / numberOfAllTickets * 100;

        String str = Long.toString(percentage);
        str += "%";

        return str;
    }
}
