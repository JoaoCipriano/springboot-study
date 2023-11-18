package com.joaolucas.study.dto;

import com.joaolucas.study.domain.Estado;

public record EstadoDTO(
        Integer id,
        String nome) {

    public EstadoDTO (Estado obj) {
        this(obj.getId(), obj.getNome());
    }
}
