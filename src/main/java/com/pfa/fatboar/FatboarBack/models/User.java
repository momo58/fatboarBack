package com.pfa.fatboar.FatboarBack.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String sub;

    private String email;

    private String imageUrl;
    
    private boolean subscribeToNewsLetters;

	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Ticket> tickets;

    //@Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }


    public User(@NotBlank @Size(max = 40) String username, @NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 100) String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(@NotBlank @Size(max = 40) String username, @NotBlank @Size(max = 40) @Email String email, @NotBlank @Size(max = 100) String imageUrl, List<Ticket> tickets) {
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.tickets = tickets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isSubscribeToNewsLetters() {
		return subscribeToNewsLetters;
	}

	public void setSubscribeToNewsLetters(boolean subscribeToNewsLetters) {
		this.subscribeToNewsLetters = subscribeToNewsLetters;
	}

    @Override
    public String toString() {
        return "Ths user have this id: " + getId();
    }
}
