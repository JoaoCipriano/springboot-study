package com.joaolucas.study.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joaolucas.study.domain.Cliente;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Transactional(readOnly = true)
    Optional<Cliente> findByEmail(String email);
}
