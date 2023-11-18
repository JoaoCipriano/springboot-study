package com.joaolucas.study.services.validation;

import com.joaolucas.study.dto.ClienteDTO;
import com.joaolucas.study.repositories.ClienteRepository;
import com.joaolucas.study.resources.exceptions.FieldMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    private final HttpServletRequest request;

    private final ClienteRepository clienteRepository;

    public ClienteUpdateValidator(HttpServletRequest request, ClienteRepository clienteRepository) {
        this.request = request;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void initialize(ClienteUpdate ann) {
        // default method
    }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        var map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        var uriId = Integer.parseInt(map.get("id"));

        var list = new ArrayList<FieldMessage>();

        var clienteOptional = clienteRepository.findByEmail(objDto.email());

        clienteOptional.ifPresent(cliente -> {
            if (!cliente.getId().equals(uriId)) {
                list.add(new FieldMessage("email", "Email já existente"));
            }
        });

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
