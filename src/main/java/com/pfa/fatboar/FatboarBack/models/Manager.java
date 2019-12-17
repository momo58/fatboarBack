package com.pfa.fatboar.FatboarBack.models;

import javax.persistence.Entity;

@Entity
public class Manager extends User {
	
	private String restaurant;
	private String perimetre;

	@Override
	public Role getRole() {
		return Role.ROLE_MANAGER;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public String getPerimetre() {
		return perimetre;
	}

	public void setPerimetre(String perimetre) {
		this.perimetre = perimetre;
	}

}
