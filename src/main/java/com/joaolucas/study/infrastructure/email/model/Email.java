package com.joaolucas.study.infrastructure.email.model;

import jakarta.validation.constraints.NotEmpty;

public record Email(
        @NotEmpty(message = "Preenchimento obrigatório")
        @jakarta.validation.constraints.Email(message = "Email inválido")
        String email) {
}
