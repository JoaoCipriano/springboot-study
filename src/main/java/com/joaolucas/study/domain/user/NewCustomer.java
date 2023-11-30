package com.joaolucas.study.domain.user;

import com.joaolucas.study.domain.validation.ClienteInsert;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

@ClienteInsert
public record NewCustomer(
        @NotEmpty(message = "Preenchimento obrigatório")
        @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
        String primeiroNome,
        @NotEmpty(message = "Preenchimento obrigatório")
        @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
        String ultimoNome,
        @NotEmpty(message = "Preenchimento obrigatório")
        @Email(message = "Email inválido")
        String email,
        @NotEmpty(message = "Preenchimento obrigatório")
        String cpfOuCnpj,
        Integer tipo,
        @NotEmpty(message = "Preenchimento obrigatório")
        String senha,
        @NotEmpty(message = "Preenchimento obrigatório")
        String logradouro,
        @NotEmpty(message = "Preenchimento obrigatório")
        String numero,
        String complemento,
        String bairro,
        @NotEmpty(message = "Preenchimento obrigatório")
        String cep,
        @NotEmpty(message = "Preenchimento obrigatório")
        String telefone1,
        String telefone2,
        String telefone3,
        Integer cidadeId) {
}
