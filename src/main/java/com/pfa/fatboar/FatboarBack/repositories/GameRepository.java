package com.pfa.fatboar.FatboarBack.repositories;

import com.pfa.fatboar.FatboarBack.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
