package com.joaolucas.study.controller.category;

import com.joaolucas.api.CategoryApi;
import com.joaolucas.model.CategoryRequest;
import com.joaolucas.model.CategoryResponse;
import com.joaolucas.model.PageableCategoryResponse;
import com.joaolucas.study.application.category.CategoryApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryApplicationService applicationService;

    @Override
    public ResponseEntity<CategoryResponse> getById(Integer id) {
        var response = applicationService.find(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<CategoryResponse>> findAll() {
        var responses = applicationService.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @Override
    public ResponseEntity<PageableCategoryResponse> findPageableCategory(Integer page,
                                                         Integer linesPerPage,
                                                         String orderBy,
                                                         String direction) {
        var pageableResponse = applicationService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(pageableResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public ResponseEntity<Void> create(CategoryRequest categoryRequest) {
        var categoryResponse = applicationService.insert(categoryRequest);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoryResponse.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public ResponseEntity<Void> update(Integer id, CategoryRequest categoryRequest) {
        applicationService.update(categoryRequest, id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public ResponseEntity<Void> delete(Integer id) {
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
