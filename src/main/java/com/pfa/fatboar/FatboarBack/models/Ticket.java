package com.pfa.fatboar.FatboarBack.models;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pfa.fatboar.FatboarBack.payload.HistoryGainResponse;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketNumber;

    private float value;

    /*
    * 0: ticket created on DB
    * 1: ticket validated by the client
    * 2: ticket validated by the restaurant employee */
    private int state;

    //@ManyToOne
    //@JoinColumn(name = "user_id", nullable = false)
    @Column(name = "user_id")
    private Long user;

    @JoinColumn(name = "gain_id", insertable = false, updatable = false)
    @ManyToOne
    private Gain gain;
    
    private Date dateValidatedByClient;
    private Date dateValidatedByEmploye;
    
    @Column(name = "gain_id")
    private Long gainId;
    
    @ManyToOne
    private TicketInsertionBatch batch;

    public Ticket() {
    }

    public Ticket(String ticketNumber, float value, int state, Long user, Gain gain) {
        this.ticketNumber = ticketNumber;
        this.value = value;
        this.state = state;
        this.user = user;
        this.gain = gain;
        this.gainId = gain.getId();
    }
    
    public Ticket(String ticketNumber, float value, int state, Long user, Long gainId, TicketInsertionBatch batch) {
        this.ticketNumber = ticketNumber;
        this.value = value;
        this.state = state;
        this.user = user;
        this.gainId = gainId;
        this.batch = batch;
    }
    
    public static Ticket mapRandom(final Long gainId, final Set<String> unavailableTicketNumbers, TicketInsertionBatch batch) {
    	String number = getRandomAndUniqueTenDigits(unavailableTicketNumbers);
    	unavailableTicketNumbers.add(number);
    	return new Ticket(number, 0F, 0, null, gainId, batch);
    }
    
    public static String getRandomAndUniqueTenDigits(final Set<String> alreadyUsedValues) {
    	String result = null;
    	do {
    		result = String.valueOf(ThreadLocalRandom.current().nextLong(1000000000l,9999999999l));
    	} while (alreadyUsedValues.contains(result));
    	return result;
    }
    
    public HistoryGainResponse toHistoryGain() {
    	return new HistoryGainResponse(this.gain.getLabel(), this.dateValidatedByClient, this.dateValidatedByEmploye);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Gain getGain() {
        return gain;
    }

    public void setGain(Gain gain) {
        this.gain = gain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public Date getDateValidatedByClient() {
		return dateValidatedByClient;
	}

	public void setDateValidatedByClient(Date dateValidatedByClient) {
		this.dateValidatedByClient = dateValidatedByClient;
	}

	public Date getDateValidatedByEmploye() {
		return dateValidatedByEmploye;
	}

	public void setDateValidatedByEmploye(Date dateValidatedByEmploye) {
		this.dateValidatedByEmploye = dateValidatedByEmploye;
	}

	public Long getGainId() {
		return gainId;
	}

	public void setGainId(Long gainId) {
		this.gainId = gainId;
	}

	public TicketInsertionBatch getBatch() {
		return batch;
	}

	public void setBatch(TicketInsertionBatch batch) {
		this.batch = batch;
	}
}
