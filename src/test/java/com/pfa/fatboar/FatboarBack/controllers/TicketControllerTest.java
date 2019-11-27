package com.pfa.fatboar.FatboarBack.controllers;

import com.pfa.fatboar.FatboarBack.models.Gain;
import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.security.UserPrincipal;
import com.pfa.fatboar.FatboarBack.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    UserPrincipal userPrincipal;

    @MockBean
    private UserService userService;

    @Test
    public void getUserGains() {

        User loggedInUser = new User();
        Gain gain = new Gain("1", "dessert au choix");
        Ticket ticket = new Ticket(341567890, 34, 2, loggedInUser.getId(), gain);
        loggedInUser.getTickets().add(ticket);

        given(userService.loggedInUser(userPrincipal)).willReturn(loggedInUser);

        try {
            mockMvc.perform(get("/api/tickets/gainhistory")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", is(gain.getLabel())));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
