package com.pfa.fatboar.FatboarBack.repositories;

import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketRepositoryTest {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    @Transactional
    public void whenFindTicketByNumber_thenReturnTicket() {
        // Given
        int ticketNumber = 1111111111;
        Optional<User> user = userRepository.findById(1l);

        Ticket ticket = new Ticket();
        ticket.setTicketNumber(ticketNumber);
        ticket.setValue(20);
        ticket.setState(0);
        ticket.setUser(user.get().getId());
        user.get().getTickets().add(ticket);
        userRepository.save(user.get());
        userRepository.flush();

        // When
        Ticket found = ticketRepository.findTicketByTicketNumber(ticketNumber);

        // Then
        assertThat(found.getTicketNumber())
                .isEqualTo(ticketNumber);
    }
}
