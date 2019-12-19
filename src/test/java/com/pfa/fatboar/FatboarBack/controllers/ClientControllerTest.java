package com.pfa.fatboar.FatboarBack.controllers;

import com.pfa.fatboar.FatboarBack.common.IntegrationTestUtil;
import com.pfa.fatboar.FatboarBack.models.Client;
import com.pfa.fatboar.FatboarBack.payload.SignupRequest;
import com.pfa.fatboar.FatboarBack.repositories.ClientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ClientRepository clientRepository;


    @Test
    public void testSignUpClient() {
        final String email = "m@m.fr";

        Optional<Client> client = clientRepository.findByEmail(email);
        if(client.isPresent()) {
            clientRepository.deleteById(client.get().getId());
        }

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("amora");
        signupRequest.setPassword("test");
        signupRequest.setEmail(email);

        byte[] body = IntegrationTestUtil.convertObjectToJsonBytes(signupRequest);

        try {
            mockMvc.perform(
                        post("/client/signup")
                        .header("Origin","*")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isOk());

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Test
    public void testSingUpClientDeux() {

    }

}
