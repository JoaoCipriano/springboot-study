package com.joaolucas.study.domain.order;

public record Address(
        Integer id,
        String publicPlace,
        String number,
        String complement,
        String neighborhood,
        String zipCode) {
}
