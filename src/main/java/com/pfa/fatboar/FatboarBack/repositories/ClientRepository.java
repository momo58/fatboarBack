package com.pfa.fatboar.FatboarBack.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pfa.fatboar.FatboarBack.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	Optional<Client> findByEmail(String email);
	boolean existsByEmail(String email);
	List<Client> findAllBySubscribeToNewsLetters(boolean subscribeToNewsLetters);

}
