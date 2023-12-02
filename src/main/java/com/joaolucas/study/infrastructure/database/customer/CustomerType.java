package com.joaolucas.study.infrastructure.database.customer;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum CustomerType {

    PESSOA_FISICA(1, "Pessoa Física"),
    PESSOA_JURIDICA(2, "Pessoa Jurídica");

    private final int code;
    private final String description;

    CustomerType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static CustomerType toEnum(Integer cod) {
        return Stream.of(CustomerType.values())
                .filter(e -> cod.equals(e.getCode()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + cod));
    }
}
