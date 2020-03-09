package com.pfa.fatboar.FatboarBack.repositories;

import com.pfa.fatboar.FatboarBack.models.Gain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GainRepository extends JpaRepository<Gain, Long> {
}
