package com.pfa.fatboar.FatboarBack.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TicketInsertionBatch {
	
	public TicketInsertionBatch() {
		
	}
	
	public TicketInsertionBatch(int numberOfTicketsInserted) {
		this.numberOfTicketsInserted = numberOfTicketsInserted;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private TicketInsertionBatchStatus status = TicketInsertionBatchStatus.PENDING;
	
	private Date startedAt = new Date();
	private Date completedAt;
	private int numberOfTicketsInserted;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TicketInsertionBatchStatus getStatus() {
		return status;
	}
	public void setStatus(TicketInsertionBatchStatus status) {
		this.status = status;
	}
	public Date getStartedAt() {
		return startedAt;
	}
	public Date getCompletedAt() {
		return completedAt;
	}
	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}
	public int getNumberOfTicketsInserted() {
		return numberOfTicketsInserted;
	}
	public void setNumberOfTicketsInserted(int numberOfTicketsInserted) {
		this.numberOfTicketsInserted = numberOfTicketsInserted;
	}
	
}
