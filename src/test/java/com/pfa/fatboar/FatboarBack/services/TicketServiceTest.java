package com.pfa.fatboar.FatboarBack.services;

import com.pfa.fatboar.FatboarBack.models.Gain;
import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.repositories.TicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketServiceTest {
    @Autowired
    TicketService ticketService;

    @Autowired
    TicketRepository ticketRepository;

    @Before
    public void setUp() throws Exception {
        Gain gain = new Gain("1", "Dessert ou entree au choix");
        Ticket ticket = new Ticket(12345678, 35, 0, null, gain);
    }

    /*@Test
    public void testHandleSubmissionTicket() throws Exception{
        ticketService.handleTicketSubmission(12345678);

        int stateAfterModif = ticketRepository.findTicketByTicketNumber(12345678).getState();

        assertThat(stateAfterModif)
                .isEqualTo(1);
    }*/
}
