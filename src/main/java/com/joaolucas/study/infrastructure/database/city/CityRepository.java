package com.joaolucas.study.infrastructure.database.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Integer> {

    @Transactional(readOnly=true)
    @Query("SELECT city FROM CityEntity city WHERE city.state.id = :stateId ORDER BY city.name")
    List<CityEntity> findAllByStateId(@Param("stateId") Integer stateId);
}
