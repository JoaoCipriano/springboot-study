package com.joaolucas.study.domain.product;

import com.joaolucas.study.infrastructure.database.product.ProductEntity;

import java.math.BigDecimal;

public record Product(
        Integer id,
        String name,
        BigDecimal price) {

    public Product(ProductEntity entity) {
        this(entity.getId(), entity.getName(), entity.getPrice());
    }
}
