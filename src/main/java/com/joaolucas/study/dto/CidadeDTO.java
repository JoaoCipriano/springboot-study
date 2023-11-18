package com.joaolucas.study.dto;

import com.joaolucas.study.domain.Cidade;

public record CidadeDTO(
        Integer id,
        String nome) {

    public CidadeDTO(Cidade obj) {
        this(obj.getId(), obj.getNome());
    }
}
