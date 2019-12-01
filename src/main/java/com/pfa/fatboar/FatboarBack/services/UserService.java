package com.pfa.fatboar.FatboarBack.services;

import com.pfa.fatboar.FatboarBack.controllers.TicketController;
import com.pfa.fatboar.FatboarBack.exception.AppException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public List<String> getUsersEmails() {
        List<String> emails = new ArrayList<>();

        List<User> users = getUsers();

        if (users.isEmpty()) {
            throw new AppException("There is nu user !");
        }
        for (User u : users) {
            emails.add(u.getEmail());
        }
        return emails;
    }

    /**
     * Returns the user who win the evoque
     * @return
     */
    public User getTheUserWhoWin() {
        //sur une liste d'id je sélectionne aléatoirement un
        //recupérer la liste d'id des users dans la base
        Random random = new Random();
        List<User> users = userRepository.findAll();

        User winner = users.get(random.nextInt(users.size()));

        return winner;
       /* List<Long> usersIds = new ArrayList<>();
        for (User u : users) {
            usersIds.add(u.getId());
        }

        usersIds.forEach(u -> System.out.println(u));*/
    }

	public Boolean toggleSubscribe(UserPrincipal userPrincipal, Boolean subscribe) {
		User user = loggedInUser(userPrincipal);
		user.setSubscribeToNewsLetters(subscribe);
		userRepository.save(user);
		return user.isSubscribeToNewsLetters();
	}
}
