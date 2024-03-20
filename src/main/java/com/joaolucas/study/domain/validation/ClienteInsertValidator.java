package com.joaolucas.study.domain.validation;

import com.joaolucas.study.controller.customer.model.CustomerRequest;
import com.joaolucas.study.controller.exception.FieldMessage;
import com.joaolucas.study.domain.validation.utils.BR;
import com.joaolucas.study.infrastructure.database.customer.CustomerRepository;
import com.joaolucas.study.infrastructure.database.customer.CustomerType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, CustomerRequest> {

    private final CustomerRepository customerRepository;

    public ClienteInsertValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void initialize(ClienteInsert ann) {
        // default method
    }

    @Override
    public boolean isValid(CustomerRequest customerRequest, ConstraintValidatorContext context) {

        var list = new ArrayList<FieldMessage>();

        if (customerRequest.type().equals(CustomerType.NATURAL_PERSON.getCode()) && !BR.isValidCPF(customerRequest.socialId())) {
            list.add(new FieldMessage("socialId", "invalid CPF"));
        }

        if (customerRequest.type().equals(CustomerType.LEGAL_PERSON.getCode()) && !BR.isValidCNPJ(customerRequest.socialId())) {
            list.add(new FieldMessage("socialId", "invalid CNPJ"));
        }

        var cliente = customerRepository.findByEmail(customerRequest.email());
        if (cliente.isPresent()) {
            list.add(new FieldMessage("email", "Email already exists"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.message())
                    .addPropertyNode(e.fieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
