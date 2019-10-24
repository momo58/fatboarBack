package com.pfa.fatboar.FatboarBack.repositories;

import com.pfa.fatboar.FatboarBack.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Optional<User> findById(long id);
    List<User> findAll();
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}

