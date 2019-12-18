package com.pfa.fatboar.FatboarBack.models;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "game")
public class Game {
	
	public static final Long THE_GAME_ID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Lob
    private String content;
    
    private Date dateFinConcours;
    
    @OneToOne
    private Client winner;

    public Game() {
    }

    public Game(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDateFinConcours() {
		return dateFinConcours;
	}

	public void setDateFinConcours(Date dateFinConcours) {
		this.dateFinConcours = dateFinConcours;
	}

	public Client getWinner() {
		return winner;
	}

	public void setWinner(Client winner) {
		this.winner = winner;
	}
}
