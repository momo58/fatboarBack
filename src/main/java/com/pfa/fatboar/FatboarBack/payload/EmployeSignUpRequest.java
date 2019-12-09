package com.pfa.fatboar.FatboarBack.payload;

public class EmployeSignUpRequest extends SignupRequest {
	
	private String restaurant;

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

}
