package com.pfa.fatboar.FatboarBack.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfa.fatboar.FatboarBack.controllers.TicketController;
import com.pfa.fatboar.FatboarBack.exception.AppException;
import com.pfa.fatboar.FatboarBack.exception.ResourceNotFoundException;
import com.pfa.fatboar.FatboarBack.models.Client;
import com.pfa.fatboar.FatboarBack.models.Game;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.repositories.ClientRepository;
import com.pfa.fatboar.FatboarBack.repositories.GameRepository;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import com.pfa.fatboar.FatboarBack.security.CurrentUser;
import com.pfa.fatboar.FatboarBack.security.UserPrincipal;

@Service
public class UserService {

    public static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    GameRepository gameRepository;
    
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
    public Client getTheUserWhoWin() {
        //sur une liste d'id je sélectionne aléatoirement un
        //recupérer la liste d'id des users dans la base
        Random random = new Random();
        List<Client> clients = clientRepository.findAll().parallelStream()
        		.filter(Client::isHasTickets)
        		.collect(Collectors.toList());

        Client winner = clients.get(random.nextInt(clients.size()));

        return winner;
    }
    
    public void defineWinner() throws Exception {
    	Game game = gameRepository.findById(Game.THE_GAME_ID).orElseThrow(() -> new Exception("Pas de game ????"));
    	game.setWinner(getTheUserWhoWin());
    	gameRepository.save(game);
    	
    }
}
