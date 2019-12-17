package com.pfa.fatboar.FatboarBack.payload;

import java.util.Date;

import com.pfa.fatboar.FatboarBack.utils.DateUtils;

public class HistoryGainResponse {

	public HistoryGainResponse() {
		
	}
	
	public HistoryGainResponse(String ticketNumber, String gain, Date dateGain, Date dateRecuperation) {
		super();
		this.setTicketNumber(ticketNumber);
		this.gain = gain;
		this.dateGain = DateUtils.formatDate(dateGain);
		this.dateRecuperation = DateUtils.formatDate(dateRecuperation);
	}

	private String ticketNumber;
	private String gain;
	private String dateGain;
	private String dateRecuperation;
	
	public String getGain() {
		return gain;
	}
	public void setGain(String gain) {
		this.gain = gain;
	}

	public String getDateGain() {
		return dateGain;
	}

	public void setDateGain(String dateGain) {
		this.dateGain = dateGain;
	}

	public String getDateRecuperation() {
		return dateRecuperation;
	}

	public void setDateRecuperation(String dateRecuperation) {
		this.dateRecuperation = dateRecuperation;
	}
	
	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

}
