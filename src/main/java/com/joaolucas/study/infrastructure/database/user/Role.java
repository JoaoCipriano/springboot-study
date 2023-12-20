package com.joaolucas.study.infrastructure.database.user;

import java.util.stream.Stream;

public enum Role {

    USER,
    ADMIN;

    public static Role toEnum(Integer cod) {
        return Stream.of(Role.values())
                .filter(e -> cod.equals(e.ordinal()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + cod));
    }
}
