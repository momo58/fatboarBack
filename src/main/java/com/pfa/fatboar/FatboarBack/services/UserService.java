package com.pfa.fatboar.FatboarBack.services;

import com.pfa.fatboar.FatboarBack.controllers.TicketController;
import com.pfa.fatboar.FatboarBack.exception.ResourceNotFoundException;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import com.pfa.fatboar.FatboarBack.security.CurrentUser;
import com.pfa.fatboar.FatboarBack.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Service
public class UserService {

    public static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    UserRepository userRepository;

    public void insertUser(User user) {
        userRepository.save(user);
    }

    /**
     * It retrieves the user logged in
     * @return User user
     */
    public User loggedInUser(@CurrentUser UserPrincipal userPrincipal) {
        logger.info("Actual logged in user returned");
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
