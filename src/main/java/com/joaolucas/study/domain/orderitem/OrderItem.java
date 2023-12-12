package com.joaolucas.study.domain.orderitem;

import com.joaolucas.study.domain.product.Product;

import java.math.BigDecimal;

public record OrderItem(BigDecimal discount,
                        Integer quantity,
                        BigDecimal price,
                        Product product) {
}
