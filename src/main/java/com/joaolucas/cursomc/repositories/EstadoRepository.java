package com.joaolucas.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.joaolucas.cursomc.domain.Estado;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    @Transactional(readOnly=true)
    List<Estado> findAllByOrderByNome();
}
