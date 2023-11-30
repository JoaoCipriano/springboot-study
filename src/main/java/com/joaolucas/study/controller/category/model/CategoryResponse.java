package com.joaolucas.study.controller.category.model;

import com.joaolucas.study.controller.product.model.ProductResponse;

import java.util.List;

public record CategoryResponse(Integer id,
                               String name,
                               List<ProductResponse> products) {
}
