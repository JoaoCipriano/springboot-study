package com.joaolucas.study.infrastructure.database.customer;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum CustomerType {

    NATURAL_PERSON(1),
    LEGAL_PERSON(2);

    private final Integer code;

    CustomerType(Integer code) {
        this.code = code;
    }

    public static CustomerType toEnum(Integer code) {
        return Stream.of(CustomerType.values())
                .filter(customerType -> customerType.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid code: " + code));
    }
}
