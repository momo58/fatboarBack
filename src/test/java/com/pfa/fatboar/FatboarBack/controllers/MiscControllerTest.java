package com.pfa.fatboar.FatboarBack.controllers;

import com.pfa.fatboar.FatboarBack.models.Game;
import com.pfa.fatboar.FatboarBack.repositories.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class MiscControllerTest {
    Game game = new Game(1L, "test text");

    @Autowired
    GameRepository mockgameRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetPresentation() {
        Game found = mockgameRepository.findById(game.getId()).get();
        try{
            mockMvc.perform(get("/misc/presentation").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
