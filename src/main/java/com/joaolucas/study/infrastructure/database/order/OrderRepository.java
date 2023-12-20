package com.joaolucas.study.infrastructure.database.order;

import com.joaolucas.study.infrastructure.database.customer.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Transactional(readOnly=true)
    Page<OrderEntity> findByCustomer(CustomerEntity customer, Pageable pageRequest);
}
