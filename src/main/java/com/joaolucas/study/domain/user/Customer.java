package com.joaolucas.study.domain.user;

import com.joaolucas.study.infrastructure.database.customer.CustomerEntity;
import com.joaolucas.study.domain.validation.ClienteUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

@ClienteUpdate
public record Customer(
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

    public Customer(CustomerEntity customerEntity) {
        this(customerEntity.getId(), customerEntity.getFirstName(), customerEntity.getLastName(), customerEntity.getEmail());
    }
}
