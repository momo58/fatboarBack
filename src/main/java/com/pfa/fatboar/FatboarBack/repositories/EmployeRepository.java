package com.pfa.fatboar.FatboarBack.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pfa.fatboar.FatboarBack.models.Employe;

public interface EmployeRepository extends JpaRepository<Employe, Long> {

	List<Employe> findAllByRestaurant(String restaurant);

}
