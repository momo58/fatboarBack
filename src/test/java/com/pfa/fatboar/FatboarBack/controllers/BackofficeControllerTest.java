package com.pfa.fatboar.FatboarBack.controllers;

import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import com.pfa.fatboar.FatboarBack.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BackofficeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @MockBean
    UserService mockUserService;

    @Test
    public void testRegisterEmploye() {
        try{
            mockMvc.perform(get("admin/signup/employe").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegisterManager() {
        try{
            mockMvc.perform(post("/admin/signup/manager").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegisterAdmin() {
        try{
            mockMvc.perform(post("/admin/signup/admin").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetContactListToCsvFormat() {
        try{
            mockMvc.perform(post("/admin/getContactList").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetEmails() {
        List<String> emails = new ArrayList<>();
        emails.add("a@a.com");
        emails.add("a@a.tn");
        emails.add("a@a.dz");

        given(mockUserService.getUsersEmails()).willReturn(emails);


    }

}
