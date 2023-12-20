package com.joaolucas.study.controller.category;

import com.joaolucas.study.application.category.CategoryApplicationService;
import com.joaolucas.study.controller.category.model.CategoryRequest;
import com.joaolucas.study.controller.category.model.CategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryApplicationService applicationService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> find(@PathVariable Integer id) {
        var response = applicationService.find(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> findAll() {
        var responses = applicationService.findAll();
        return ResponseEntity.ok().body(responses);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoryResponse>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        var pageableResponse = applicationService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(pageableResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CategoryRequest categoryRequest) {
        var categoryResponse = applicationService.insert(categoryRequest);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoryResponse.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CategoryRequest categoryRequest, @PathVariable Integer id) {
        applicationService.update(categoryRequest, id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
