package com.pfa.fatboar.FatboarBack.models;

import javax.persistence.Entity;

@Entity
public class Employe extends User {

	public Employe(String username, String email, String password) {
		super(username, email, password);
		super.setRole(Role.ROLE_EMPLOYE);
	}

	@Override
	public Role getRole() {
		return Role.ROLE_EMPLOYE;
	}

}
