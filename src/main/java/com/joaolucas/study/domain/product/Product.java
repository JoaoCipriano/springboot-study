package com.joaolucas.study.domain.product;

import com.joaolucas.study.infrastructure.database.product.ProductEntity;

public record Product(
        Integer id,
        String name,
        Double price) {

    public Product(ProductEntity entity) {
        this(entity.getId(), entity.getName(), entity.getPrice());
    }
}
