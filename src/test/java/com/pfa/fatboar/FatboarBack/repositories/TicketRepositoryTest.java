//package com.pfa.fatboar.FatboarBack.repositories;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.pfa.fatboar.FatboarBack.models.Client;
//import com.pfa.fatboar.FatboarBack.models.Ticket;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@TestPropertySource(
//		locations = "classpath:application-test.properties")
//public class TicketRepositoryTest {
//
//	@Autowired
//	TicketRepository ticketRepository;
//
//	@Autowired
//	UserRepository userRepository;
//
//	String ticketNumber = "1111111119";
//
//	@Before
//	public void setUp() {
//		Client user = new Client("amira", "a@a.com","pwd");
//		Ticket ticket = new Ticket();
//		ticket.setTicketNumber(ticketNumber);
//		ticket.setValue(20);
//		ticket.setState(0);
//		ticket.setUser(user.getId()); // user associated to the ticket
//		user.getTickets().add(ticket);
//		userRepository.save(user);
//		userRepository.flush();
//	}
//
//	@Test
//	@Transactional
//	public void whenFindTicketByNumber_thenReturnTicket() {
//		// Given
//
//		// When
//		Ticket ticket = ticketRepository.findByTicketNumber(ticketNumber);
//
//		// Then
//		assertThat(ticket.getTicketNumber())
//		.isEqualTo(ticketNumber);
//	}
//}
