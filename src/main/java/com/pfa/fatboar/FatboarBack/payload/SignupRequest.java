package com.pfa.fatboar.FatboarBack.payload;

public class SignupRequest {
    private String username;
    private String email;
    private String password;
    private String prenom;
    private String nom;
    private boolean subscribe;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isSubscribe() {
		return subscribe;
	}

	public void setSubscribe(boolean subscribe) {
		this.subscribe = subscribe;
	}
}
