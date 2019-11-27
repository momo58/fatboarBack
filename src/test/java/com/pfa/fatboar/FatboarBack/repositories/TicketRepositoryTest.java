package com.pfa.fatboar.FatboarBack.repositories;

import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class TicketRepositoryTest {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    int ticketNumber = 23415678;

    @Before
    public void setUp() {
        User user = new User("amira", "a@a.com","pwd");
        Ticket ticket = new Ticket();
        ticket.setTicketNumber(ticketNumber);
        ticket.setValue(20);
        ticket.setState(0);
        ticket.setUser(user.getId()); // user associated to the ticket
        user.getTickets().add(ticket);
        userRepository.save(user);
        userRepository.flush();
    }

    @Test
    @Transactional
    public void whenFindTicketByNumber_thenReturnTicket() {
        // Given

        // When
        Ticket ticket = ticketRepository.findTicketByTicketNumber(ticketNumber);

        // Then
        assertThat(ticket.getTicketNumber())
                .isEqualTo(ticketNumber);
    }
}
