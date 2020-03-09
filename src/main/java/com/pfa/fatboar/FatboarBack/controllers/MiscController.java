package com.pfa.fatboar.FatboarBack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfa.fatboar.FatboarBack.models.Game;
import com.pfa.fatboar.FatboarBack.repositories.GameRepository;

@RestController
@RequestMapping("/misc")
public class MiscController {
	
	@Autowired
	private GameRepository gameRepository;
	
	@GetMapping("/presentation")
	public ResponseEntity<Game> getPresentation() {
		return ResponseEntity.ok(gameRepository.findById(Game.THE_GAME_ID)
				.orElseThrow(() -> new RuntimeException("Aucune présentation du jeu concours avec l'id 1")));
	}

}
