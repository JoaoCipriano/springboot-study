package com.joaolucas.study.application.category.mapper;

import com.joaolucas.model.CategoryRequest;
import com.joaolucas.model.CategoryResponse;
import com.joaolucas.model.PageCategoryResponse;
import com.joaolucas.study.infrastructure.database.category.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(CategoryEntity entity);

    List<CategoryResponse> toResponses(List<CategoryEntity> entities);

    default PageCategoryResponse toPageableResponse(Page<CategoryEntity> pageableEntity) {
        return new PageCategoryResponse()
                .content(toResponses(pageableEntity.toList()))
                .totalPages(pageableEntity.getPageable().getPageSize())
                .totalElements(pageableEntity.getTotalElements());

    }

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "id", ignore = true)
    CategoryEntity toEntity(CategoryRequest categoryRequest);
}
