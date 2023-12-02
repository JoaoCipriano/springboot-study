package com.joaolucas.study.domain.order;

public record ShippingAddressRequest(
        Integer id,
        String publicPlace,
        String number,
        String complement,
        String neighborhood,
        String zipCode) {
}
