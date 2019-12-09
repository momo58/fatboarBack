package com.pfa.fatboar.FatboarBack.models;

import javax.persistence.Entity;

@Entity
public class Admin extends User {

	@Override
	public Role getRole() {
		return Role.ROLE_ADMIN;
	}

}
