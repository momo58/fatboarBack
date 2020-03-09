package com.pfa.fatboar.FatboarBack.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfa.fatboar.FatboarBack.exception.ResourceNotFoundException;
import com.pfa.fatboar.FatboarBack.models.Client;
import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.payload.HistoryGainResponse;
import com.pfa.fatboar.FatboarBack.repositories.ClientRepository;
import com.pfa.fatboar.FatboarBack.repositories.TicketRepository;
import com.pfa.fatboar.FatboarBack.security.CurrentUser;
import com.pfa.fatboar.FatboarBack.security.UserPrincipal;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private TicketRepository ticketRepository;

    public Client loggedInClient(final @CurrentUser UserPrincipal userPrincipal) {
        return clientRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

	public Boolean toggleSubscribe(final UserPrincipal userPrincipal, final Boolean subscribe) {
		final Client client = loggedInClient(userPrincipal);
		client.setSubscribeToNewsLetters(subscribe);
		clientRepository.save(client);
		return client.isSubscribeToNewsLetters();
	}

	public File getAllSubscribedClientsToCsv() throws FileNotFoundException {
		final File csvOutputFile = new File("export_subscribed_client.csv");

		try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
			clientRepository.findAllBySubscribeToNewsLetters(true).stream()
			.map(Client::getEmail)
			.forEach(pw::println);
		}
		return csvOutputFile;
	}

	public List<HistoryGainResponse> findAllGains(Client userLoggedIn) {
		return ticketRepository.findByUser(userLoggedIn.getId()).stream()
		.map(Ticket::toHistoryGain)
		.collect(Collectors.toList());
		
	}

}
