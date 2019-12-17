package com.pfa.fatboar.FatboarBack.services;

import com.pfa.fatboar.FatboarBack.models.Client;
import com.pfa.fatboar.FatboarBack.models.Gain;
import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.payload.HistoryGainResponse;
import com.pfa.fatboar.FatboarBack.repositories.ClientRepository;
import com.pfa.fatboar.FatboarBack.repositories.TicketRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientServiceTest {
    Client loggedInClient = new Client();
    Gain gain = new Gain("dessert au choix");

    @Autowired
    ClientService clientService;

    @MockBean
    private ClientRepository mockclientRepository;

    @MockBean
    private TicketRepository mockticketRepository;

    @Test
    public void testFindAllGains() {
        Ticket ticket = new Ticket("1111111112", 34, 2, loggedInClient.getId(), gain);
        List<Ticket> list = new ArrayList<>();
        list.add(ticket);
        loggedInClient.getTickets().add(ticket);

        when(mockticketRepository.findByUser(loggedInClient.getId())).thenReturn(list);

        List<HistoryGainResponse> historyGain = clientService.findAllGains(loggedInClient);

        assertThat(historyGain)
                .isNotNull()
                .isNotEmpty()
                .allMatch(p -> p.getGain()
                        .toLowerCase()
                        .contains("au choix"));
    }

    @Test
    public void testToggleSubscribe() {
        loggedInClient.setSubscribeToNewsLetters(true);
        Boolean isSub = loggedInClient.isSubscribeToNewsLetters();
        assertTrue(isSub);
    }


    @Test
    public void testLoggedInClient() {
        loggedInClient.setId(4L);
        Optional o = Optional.of(loggedInClient);
        when(mockclientRepository.findById(loggedInClient.getId())).thenReturn(o);
        assertThat(o).isNotEmpty();
    }
}
