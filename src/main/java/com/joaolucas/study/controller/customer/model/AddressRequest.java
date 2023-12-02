package com.joaolucas.study.controller.customer.model;

import jakarta.validation.constraints.NotEmpty;

public record AddressRequest(@NotEmpty(message = "Preenchimento obrigatório")
                             String publicPlace,
                             @NotEmpty(message = "Preenchimento obrigatório")
                             String number,
                             String complement,
                             String neighborhood,
                             @NotEmpty(message = "Preenchimento obrigatório")
                             String zipCode) {
}
