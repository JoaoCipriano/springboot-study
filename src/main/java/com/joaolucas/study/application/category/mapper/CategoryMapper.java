package com.joaolucas.study.application.category.mapper;

import com.joaolucas.study.controller.category.model.CategoryRequest;
import com.joaolucas.study.controller.category.model.CategoryResponse;
import com.joaolucas.study.infrastructure.database.category.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(CategoryEntity entity);

    List<CategoryResponse> toResponses(List<CategoryEntity> entities);

    default Page<CategoryResponse> toPageableResponse(Page<CategoryEntity> pageableEntity) {
        return new PageImpl<>(
                toResponses(pageableEntity.toList()),
                pageableEntity.getPageable(),
                pageableEntity.getTotalElements()
        );
    }

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "id", ignore = true)
    CategoryEntity toEntity(CategoryRequest categoryRequest);
}
