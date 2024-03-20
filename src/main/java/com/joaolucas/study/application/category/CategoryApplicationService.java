package com.joaolucas.study.application.category;

import com.joaolucas.model.CategoryRequest;
import com.joaolucas.model.CategoryResponse;
import com.joaolucas.model.PageableCategoryResponse;
import com.joaolucas.study.application.category.mapper.CategoryMapper;
import com.joaolucas.study.domain.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryApplicationService {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryResponse find(Integer id) {
        return categoryMapper.toResponse(categoryService.find(id));
    }

    public List<CategoryResponse> findAll() {
        return categoryMapper.toResponses(categoryService.findAll());
    }

    public PageableCategoryResponse findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        var pageableEntity = categoryService.findPage(page, linesPerPage, orderBy, direction);
        return categoryMapper.toPageableResponse(pageableEntity);
    }

    public CategoryResponse insert(CategoryRequest categoryRequest) {
        var categoryEntity = categoryMapper.toEntity(categoryRequest);
        return categoryMapper.toResponse(categoryService.insert(categoryEntity));
    }

    public void update(CategoryRequest categoryRequest, Integer id) {
        var categoryEntity = categoryMapper.toEntity(categoryRequest);
        categoryService.update(categoryEntity, id);
    }

    public void delete(Integer id) {
        categoryService.delete(id);
    }
}
