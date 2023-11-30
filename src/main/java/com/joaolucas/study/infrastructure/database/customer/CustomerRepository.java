package com.joaolucas.study.infrastructure.database.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    @Transactional(readOnly = true)
    Optional<CustomerEntity> findByEmail(String email);
}
