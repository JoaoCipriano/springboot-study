package com.joaolucas.study.dto;

import com.joaolucas.study.domain.Categoria;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record CategoriaDTO(
        Integer id,
        @NotEmpty(message = "Preenchimento obrigatório")
        @Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
        String nome) {

    public CategoriaDTO(Categoria entity) {
        this(entity.getId(), entity.getNome());
    }
}
