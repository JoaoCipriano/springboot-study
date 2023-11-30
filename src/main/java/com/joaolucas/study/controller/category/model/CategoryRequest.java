package com.joaolucas.study.controller.category.model;

import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public record CategoryRequest(
        @NotEmpty(message = "Preenchimento obrigatório")
        @Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
        String name) {
}
