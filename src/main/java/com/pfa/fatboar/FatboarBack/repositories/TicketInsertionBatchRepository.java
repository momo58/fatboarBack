package com.pfa.fatboar.FatboarBack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfa.fatboar.FatboarBack.models.TicketInsertionBatch;

@Repository
public interface TicketInsertionBatchRepository extends JpaRepository<TicketInsertionBatch, Long> {

}
