package com.joaolucas.study.infrastructure.database.state;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StateRepository extends JpaRepository<StateEntity, Integer> {

    @Transactional(readOnly=true)
    List<StateEntity> findAllByOrderByName();
}
