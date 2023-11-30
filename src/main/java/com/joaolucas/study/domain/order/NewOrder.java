package com.joaolucas.study.domain.order;

import com.joaolucas.study.domain.user.Customer;
import com.joaolucas.study.domain.orderitem.OrderItem;
import com.joaolucas.study.infrastructure.database.payment.PaymentEntity;

import java.util.Set;

public record NewOrder(
        Customer customer,
        Address shippingAddress,
        PaymentEntity paymentEntity,
        Set<OrderItem> items) {
}
