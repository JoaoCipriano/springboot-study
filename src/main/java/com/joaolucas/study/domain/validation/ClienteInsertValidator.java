package com.joaolucas.study.domain.validation;

import com.joaolucas.study.infrastructure.database.customer.CustomerType;
import com.joaolucas.study.domain.user.NewCustomer;
import com.joaolucas.study.infrastructure.database.customer.CustomerRepository;
import com.joaolucas.study.controller.exceptions.FieldMessage;
import com.joaolucas.study.domain.validation.utils.BR;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, NewCustomer> {

    private final CustomerRepository customerRepository;

    public ClienteInsertValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void initialize(ClienteInsert ann) {
        // default method
    }

    @Override
    public boolean isValid(NewCustomer objDto, ConstraintValidatorContext context) {

        var list = new ArrayList<FieldMessage>();

        if (objDto.tipo().equals(CustomerType.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.cpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (objDto.tipo().equals(CustomerType.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.cpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        var cliente = customerRepository.findByEmail(objDto.email());
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
