package com.pfa.fatboar.FatboarBack.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Client extends User {

	public Client() {
		super.setRole(Role.ROLE_CLIENT);
	}

	public Client(String username, String email, String password) {
		super(username, email, password);
		super.setRole(Role.ROLE_CLIENT);
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private List<Ticket> tickets = new ArrayList<>();

	private boolean subscribeToNewsLetters = false;
	private boolean hasTickets = false;

	@Override
	public Role getRole() {
		return Role.ROLE_CLIENT;
	}

	public boolean isSubscribeToNewsLetters() {
		return subscribeToNewsLetters;
	}

	public void setSubscribeToNewsLetters(boolean subscribeToNewsLetters) {
		this.subscribeToNewsLetters = subscribeToNewsLetters;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public boolean isHasTickets() {
		return hasTickets;
	}

	public void setHasTickets(boolean hasTickets) {
		this.hasTickets = hasTickets;
	}

}