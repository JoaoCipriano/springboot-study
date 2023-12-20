package com.joaolucas.study.controller.customer.model;

import com.joaolucas.study.infrastructure.database.customer.CustomerType;

import java.util.List;

public record CustomerResponse(Integer id,
                               String firstName,
                               String lastName,
                               String email,
                               String socialId,
                               CustomerType type,
                               List<AddressResponse> addresses,
                               List<String> phones) {
}
