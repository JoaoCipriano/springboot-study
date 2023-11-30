package com.joaolucas.study.domain.orderitem;

import com.joaolucas.study.domain.product.Product;

public record OrderItem(
        Double discount,
        Integer quantity,
        Double price,
        Product product) {
}
