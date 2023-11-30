package com.joaolucas.study.domain.validation;

import com.joaolucas.study.domain.user.Customer;
import com.joaolucas.study.infrastructure.database.customer.CustomerRepository;
import com.joaolucas.study.controller.exception.FieldMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, Customer> {

    private final HttpServletRequest request;

    private final CustomerRepository customerRepository;

    public ClienteUpdateValidator(HttpServletRequest request, CustomerRepository customerRepository) {
        this.request = request;
        this.customerRepository = customerRepository;
    }

    @Override
    public void initialize(ClienteUpdate ann) {
        // default method
    }

    @Override
    public boolean isValid(Customer objDto, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        var map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        var uriId = Integer.parseInt(map.get("id"));

        var fieldMessages = new ArrayList<FieldMessage>();

        var clienteOptional = customerRepository.findByEmail(objDto.email());

        clienteOptional.ifPresent(cliente -> {
            if (!cliente.getId().equals(uriId)) {
                fieldMessages.add(new FieldMessage("email", "Email já existente"));
            }
        });

        for (var fieldMessage : fieldMessages) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldMessage.message())
                    .addPropertyNode(fieldMessage.fieldName()).addConstraintViolation();
        }
        return fieldMessages.isEmpty();
    }
}
