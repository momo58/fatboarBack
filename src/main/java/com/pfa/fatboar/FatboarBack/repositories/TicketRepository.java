package com.pfa.fatboar.FatboarBack.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfa.fatboar.FatboarBack.models.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByTicketNumber(String ticketNumber);
    int countByState(int state);
    long count();
	List<Ticket> findByUser(Long userId);
}
