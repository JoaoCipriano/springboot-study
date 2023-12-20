package com.joaolucas.study.application.product.mapper;

import com.joaolucas.study.controller.product.model.ProductResponse;
import com.joaolucas.study.infrastructure.database.product.ProductEntity;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toResponse(ProductEntity entity);

    List<ProductResponse> toResponses(List<ProductEntity> entities);

    default Page<ProductResponse> toPageableResponse(Page<ProductEntity> pageableEntities) {
        return new PageImpl<>(
                toResponses(pageableEntities.toList()),
                pageableEntities.getPageable(),
                pageableEntities.getTotalElements()
        );
    }
}
