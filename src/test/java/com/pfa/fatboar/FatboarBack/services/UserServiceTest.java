package com.pfa.fatboar.FatboarBack.services;

import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @MockBean
    UserRepository mockUserRepository;

    @Test
    public void testGetUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("aa", "aa", "aaaa"));
        users.add(new User("zz", "zz", "zaaa"));

        when(mockUserRepository.findAll()).thenReturn(users);

        assertThat(users).isNotNull()
                .isNotEmpty();
    }

}
