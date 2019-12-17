package com.pfa.fatboar.FatboarBack.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfa.fatboar.FatboarBack.models.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

	List<Manager> findAllByPerimetre(String perimetre);

}
