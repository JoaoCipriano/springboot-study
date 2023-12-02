package com.joaolucas.study.domain.customer.model;

import com.joaolucas.study.infrastructure.database.customer.CustomerType;

import java.util.List;

public record Customer(Integer id,
                       String firstName,
                       String lastName,
                       String email,
                       String socialId,
                       CustomerType type,
                       List<Address> addresses,
                       List<String> phones) {
}
