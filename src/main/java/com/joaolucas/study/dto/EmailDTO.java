package com.joaolucas.study.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record EmailDTO(
        @NotEmpty(message = "Preenchimento obrigatório")
        @Email(message = "Email inválido")
        String email) {
}
