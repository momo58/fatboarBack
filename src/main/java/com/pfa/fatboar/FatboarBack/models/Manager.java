package com.pfa.fatboar.FatboarBack.models;

import javax.persistence.Entity;

@Entity
public class Manager extends User {

	@Override
	public Role getRole() {
		return Role.ROLE_MANAGER;
	}

}
