package com.pfa.fatboar.FatboarBack.models;

import javax.persistence.Entity;

@Entity
public class Employe extends User {
	
	private String restaurant;

	public Employe(String username, String email, String password) {
		super(username, email, password);
		super.setRole(Role.ROLE_EMPLOYE);
	}

	@Override
	public Role getRole() {
		return Role.ROLE_EMPLOYE;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

}
