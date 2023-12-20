package com.joaolucas.study.controller.order.model;

import com.joaolucas.study.controller.customer.model.AddressResponse;
import com.joaolucas.study.controller.customer.model.CustomerResponse;

import java.time.LocalDate;

public record OrderResponse(Integer id,
                            LocalDate instant,
                            CustomerResponse customer,
                            PaymentResponse payment,
                            AddressResponse shippingAddress) {
}
