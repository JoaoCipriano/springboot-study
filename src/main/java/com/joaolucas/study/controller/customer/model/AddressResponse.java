package com.joaolucas.study.controller.customer.model;

public record AddressResponse(Integer id,
                              String publicPlace,
                              String number,
                              String complement,
                              String neighborhood,
                              String zipCode,
                              String city) {
}
