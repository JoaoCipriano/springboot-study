package com.joaolucas.study.application.category.mapper;

import com.joaolucas.model.CategoryRequest;
import com.joaolucas.model.CategoryResponse;
import com.joaolucas.model.PageableCategoryResponse;
import com.joaolucas.study.infrastructure.database.category.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(CategoryEntity entity);

    List<CategoryResponse> toResponses(List<CategoryEntity> entities);

    default PageableCategoryResponse toPageableResponse(Page<CategoryEntity> pageableEntity) {
        return new PageableCategoryResponse()
                .content(toResponses(pageableEntity.toList()))
                .totalPages(pageableEntity.getPageable().getPageSize())
                .totalElements(pageableEntity.getTotalElements());

    }

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "id", ignore = true)
    CategoryEntity toEntity(CategoryRequest categoryRequest);
}
