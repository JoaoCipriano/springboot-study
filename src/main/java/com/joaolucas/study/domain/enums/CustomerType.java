package com.joaolucas.study.domain.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum CustomerType {

    PESSOAFISICA(1, "Pessoa Física"),
    PESSOAJURIDICA(2, "Pessoa Jurídica");

    private final int cod;
    private final String descricao;

    CustomerType(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public static CustomerType toEnum(Integer cod) {
        return Stream.of(CustomerType.values())
                .filter(e -> cod.equals(e.getCod()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + cod));
    }
}
