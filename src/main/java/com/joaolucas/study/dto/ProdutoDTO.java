package com.joaolucas.study.dto;

import com.joaolucas.study.domain.Produto;

public record ProdutoDTO(
        Integer id,
        String nome,
        Double preco) {

    public ProdutoDTO(Produto entity) {
        this(entity.getId(), entity.getNome(), entity.getPreco());
    }
}
