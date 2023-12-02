package com.joaolucas.study.controller.order.model;

import com.joaolucas.study.controller.customer.model.CustomerResponse;
import com.joaolucas.study.domain.order.ShippingAddressRequest;
import com.joaolucas.study.domain.orderitem.OrderItem;
import com.joaolucas.study.infrastructure.database.payment.PaymentEntity;

import java.util.Set;

public record OrderRequest(
        CustomerResponse customer,
        ShippingAddressRequest shippingAddress,
        PaymentEntity paymentEntity,
        Set<OrderItem> items) {
}
