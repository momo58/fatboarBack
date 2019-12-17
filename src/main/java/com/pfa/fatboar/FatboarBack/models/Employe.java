package com.pfa.fatboar.FatboarBack.models;

import javax.persistence.Entity;

@Entity
public class Employe extends User {

	public Employe() {

	}

	public Employe(String username, String email, String password) {
		super(username, email, password);
		super.setRole(Role.ROLE_EMPLOYE);
	}

	private String restaurant;

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
