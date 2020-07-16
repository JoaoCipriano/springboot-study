package com.joaolucas.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joaolucas.cursomc.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}
