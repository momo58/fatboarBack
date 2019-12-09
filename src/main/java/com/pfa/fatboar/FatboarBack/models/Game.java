package com.pfa.fatboar.FatboarBack.models;

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

    public Game() {
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
}
