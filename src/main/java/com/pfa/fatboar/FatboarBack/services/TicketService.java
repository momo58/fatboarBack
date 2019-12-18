package com.pfa.fatboar.FatboarBack.services;

import static com.pfa.fatboar.FatboarBack.common.Constants.TICKETS_PER_MULTIPLE;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfa.fatboar.FatboarBack.common.Constants;
import com.pfa.fatboar.FatboarBack.common.GainInfo;
import com.pfa.fatboar.FatboarBack.exception.AppException;
import com.pfa.fatboar.FatboarBack.models.Client;
import com.pfa.fatboar.FatboarBack.models.Ticket;
import com.pfa.fatboar.FatboarBack.models.TicketInsertionBatch;
import com.pfa.fatboar.FatboarBack.models.TicketInsertionBatchStatus;
import com.pfa.fatboar.FatboarBack.models.User;
import com.pfa.fatboar.FatboarBack.payload.InsertTicketsRequest;
import com.pfa.fatboar.FatboarBack.repositories.ClientRepository;
import com.pfa.fatboar.FatboarBack.repositories.TicketInsertionBatchRepository;
import com.pfa.fatboar.FatboarBack.repositories.TicketRepository;
import com.pfa.fatboar.FatboarBack.repositories.UserRepository;
import com.pfa.fatboar.FatboarBack.security.CurrentUser;
import com.pfa.fatboar.FatboarBack.security.UserPrincipal;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private TicketInsertionBatchRepository ticketInsertionBatchRepository;

    /**
     *
     * @param ticketNumber
     * @return Ticket
     * This method returns a ticket from a number passed as param
     */
    public Optional<Ticket> getTicket(String ticketNumber) {
        return ticketRepository.findByTicketNumber(ticketNumber);
    }

    public Ticket insertTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    /**
     *
     * @param number
     * @throws Exception
     * This method handle the ticket number submission
     */
    @Transactional
    public String handleTicketSubmission(String number, @CurrentUser UserPrincipal userPrincipal) throws Exception {
        //Verifications
        Ticket ticket = getTicket(number).orElse(null);
        if (ticket == null) {
            throw new AppException("Votre numéro est incorrect.");
        } else if (ticket.getState() > 0 && ticket.getUser().equals(userPrincipal.getId())) {
            throw new AppException("Vous avez déjà utilisé ce ticket");
        } else if (ticket.getState() > 0) {
            throw new AppException("Ce numéro a déjà été utilisé");
        } else {
            User userLoggedIn = userService.loggedInUser(userPrincipal);
        	Client client = clientRepository.findById(userLoggedIn.getId()).orElseThrow(() -> new Exception("Vous devez être un client pour jouer"));
        	client.setHasTickets(true);
        	clientRepository.save(client);
            ticket.setUser(userLoggedIn.getId());
            ticket.setDateValidatedByClient(new Date());
            //state 1 means that the ticket is well associated to a user and its gain is not yet consumed
            ticket.setState(1);
            ticketRepository.save(ticket);

            return ticket.getGain().getLabel();
        }
    }

    /**
     *
     * @param number
     * @throws Exception
     * This method handle the ticket number submission by the employee
     */
    @Transactional
    public boolean handleTicketSubmissionByEmployee(String number, @CurrentUser UserPrincipal userPrincipal) throws Exception {

        Ticket ticket = getTicket(number).orElse(null);
        if (ticket == null) {
            throw new AppException("Employee: The ticket you have introduce is not found ! Try again. ");
        } else {
           /* User userTicketOwner = userRepository.findById(ticket.getUser())
                    .orElseThrow(() -> new AppException("User not found !"));*/

            //state 2 means that the ticket is well validated by the restaurant employee
            ticket.setState(2);
            ticket.setDateValidatedByEmploye(new Date());
            ticketRepository.save(ticket);
            return true;
        }
    }

    /**
     * Returns tickets associated to a client
     */
    public String getclientParticipationStat() {

        int numberOfTicketsAssociatedToaClient = ticketRepository.countByState(1);
        long numberOfAllTickets = ticketRepository.count();

        long percentage = numberOfTicketsAssociatedToaClient / numberOfAllTickets * 100;

        String str = Long.toString(percentage);
        str += "%";

        return str;
    }
    
	public TicketInsertionBatch batchInsertTickets(final InsertTicketsRequest request) {
    	final TicketInsertionBatch batch = insertNewBatch(request.getMultiple());
		final Set<String> unavailableTicketNumbers = findUnavailableTicketNumbers();
		doBatchInsertTickets(unavailableTicketNumbers, request.getMultiple(), batch);
		return batch;
	}
	
    @Async
	public void doBatchInsertTickets(Set<String> unavailableTicketNumbers, Integer multiple, TicketInsertionBatch batch) {
    	final int numberOfTicketsInserted = ticketRepository.saveAll(IntStream.range(0, multiple).parallel()
				.mapToObj($ -> generateOneBatch(unavailableTicketNumbers, batch))
				.flatMap(List::stream)
				.collect(Collectors.toList())).size();
		
		finalizeTicketInsertionBatch(batch, numberOfTicketsInserted);
	}

	private Set<String> findUnavailableTicketNumbers() {
		return ticketRepository.findAll().stream()
		.map(Ticket::getTicketNumber)
		.collect(Collectors.toSet());
	}

	private void finalizeTicketInsertionBatch(final TicketInsertionBatch batch, final int numberOfTicketsInserted) {
		ticketInsertionBatchRepository.findById(batch.getId()).ifPresent(b -> {
			b.setCompletedAt(new Date());
			b.setNumberOfTicketsInserted(numberOfTicketsInserted);
			b.setStatus(TicketInsertionBatchStatus.COMPLETED);
			ticketInsertionBatchRepository.save(b);
		});		
	}

	private TicketInsertionBatch insertNewBatch(Integer multiple) {
		return ticketInsertionBatchRepository.save(new TicketInsertionBatch(multiple * Constants.TICKETS_PER_MULTIPLE));
	}

	private List<Ticket> generateOneBatch(final Set<String> unavailableTicketNumbers, TicketInsertionBatch batch) {
		return Stream.of(GainInfo.values())
		.flatMap(gainInfo -> generateOneSpecificTicketStream(gainInfo, TICKETS_PER_MULTIPLE, unavailableTicketNumbers, batch))
		.collect(Collectors.toList());
	}
	
	private Stream<Ticket> generateOneSpecificTicketStream(final GainInfo gainInfo, final int batchSize, final Set<String> unavailableTicketNumbers, TicketInsertionBatch batch) {
		final int amountOfSpecificTicketsToCreate = gainInfo.getProbabilite() * batchSize / 100;
		return IntStream.range(0, amountOfSpecificTicketsToCreate).boxed()
		.map($ -> Ticket.mapRandom(gainInfo.getGainId(), unavailableTicketNumbers, batch));
	}
	
}
