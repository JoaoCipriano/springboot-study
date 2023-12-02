package com.joaolucas.study.controller.customer.model;

import com.joaolucas.study.domain.validation.ClienteInsert;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@ClienteInsert
public record CustomerRequest(
        @NotEmpty(message = "Preenchimento obrigatório")
        @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
        String firstName,
        @NotEmpty(message = "Preenchimento obrigatório")
        @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
        String lastName,
        @NotEmpty(message = "Preenchimento obrigatório")
        @Email(message = "Email inválido")
        String email,
        @NotEmpty(message = "Preenchimento obrigatório")
        String socialId,
        Integer type,
        @NotEmpty(message = "Preenchimento obrigatório")
        String password,
        @NotNull
        List<AddressRequest> addresses,
        @NotEmpty(message = "Preenchimento obrigatório")
        List<String> phones) {
}
