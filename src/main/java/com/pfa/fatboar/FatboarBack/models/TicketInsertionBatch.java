package com.pfa.fatboar.FatboarBack.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class TicketInsertionBatch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date startedAt = new Date();
	private Date completedAt;
	private int numberOfTicketsInserted;
	
	public TicketInsertionBatch() {
		
	}
	
	public TicketInsertionBatch(int numberOfTicketsInserted) {
		this.numberOfTicketsInserted = numberOfTicketsInserted;
	}
	
	@Enumerated(EnumType.STRING)
	private TicketInsertionBatchStatus status = TicketInsertionBatchStatus.PENDING;

	
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
