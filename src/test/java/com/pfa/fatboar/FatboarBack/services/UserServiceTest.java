package com.pfa.fatboar.FatboarBack.services;

import com.pfa.fatboar.FatboarBack.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@DataJpaTest
public class UserServiceTest {

    private  User user;

    @Autowired
    UserService userService;

    @Before
    public void setUp() throws Exception {
        user = new User("Amira","a@a.com","test");
    }

    @Test
    public void insertUser() {
        userService.insertUser(user);
    }
}
