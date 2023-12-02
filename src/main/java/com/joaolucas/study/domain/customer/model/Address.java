package com.joaolucas.study.domain.customer.model;

public record Address(Integer id,
                      String publicPlace,
                      String number,
                      String complement,
                      String neighborhood,
                      String zipCode,
                      String city) {
}
