package com.joaolucas.study.dto;

import com.joaolucas.study.domain.Cliente;
import com.joaolucas.study.services.validation.ClienteUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

@ClienteUpdate
public record ClienteDTO(
        Integer id,
        @NotEmpty(message = "Preenchimento obrigatório")
        @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
        String firstName,
        @NotEmpty(message = "Preenchimento obrigatório")
        @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
        String lastName,
        @NotEmpty(message = "Preenchimento obrigatório")
        @Email(message = "Email inválido")
        String email) {

    public ClienteDTO(Cliente cliente) {
        this(cliente.getId(), cliente.getFirstName(), cliente.getLastName(), cliente.getEmail());
    }
}
