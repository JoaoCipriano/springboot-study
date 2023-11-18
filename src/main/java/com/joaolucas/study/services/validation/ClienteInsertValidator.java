package com.joaolucas.study.services.validation;

import com.joaolucas.study.domain.enums.CustomerType;
import com.joaolucas.study.dto.ClienteNewDTO;
import com.joaolucas.study.repositories.ClienteRepository;
import com.joaolucas.study.resources.exceptions.FieldMessage;
import com.joaolucas.study.services.validation.utils.BR;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    private final ClienteRepository clienteRepository;

    public ClienteInsertValidator(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void initialize(ClienteInsert ann) {
        // default method
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

        var list = new ArrayList<FieldMessage>();

        if (objDto.tipo().equals(CustomerType.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.cpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (objDto.tipo().equals(CustomerType.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.cpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        var cliente = clienteRepository.findByEmail(objDto.email());
        if (cliente.isPresent()) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
