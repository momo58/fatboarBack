package com.pfa.fatboar.FatboarBack.models;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Admin extends User {
	
	public Admin() {
		
	}
	
	public Admin(@NotBlank @Size(max = 40) String username, @NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 100) String password, String perimetre) {
        super(username, email, password);
        super.setRole(getRole());
        this.perimetre = perimetre;
    }
	
	private String perimetre;

	@Override
	public Role getRole() {
		return Role.ROLE_ADMIN;
	}

	public String getPerimetre() {
		return perimetre;
	}

	public void setPerimetre(String perimetre) {
		this.perimetre = perimetre;
	}

}
