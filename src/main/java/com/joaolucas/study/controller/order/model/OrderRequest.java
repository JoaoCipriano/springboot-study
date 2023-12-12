package com.joaolucas.study.controller.order.model;

import com.joaolucas.study.domain.order.ShippingAddressRequest;
import com.joaolucas.study.domain.orderitem.OrderItem;

import java.util.Set;

public record OrderRequest(CustomerRequest customer,
                           ShippingAddressRequest shippingAddress,
                           PaymentRequest payment,
                           Set<OrderItem> items) {
}
