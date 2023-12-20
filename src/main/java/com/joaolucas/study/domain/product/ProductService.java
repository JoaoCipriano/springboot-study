package com.joaolucas.study.domain.product;

import com.joaolucas.study.domain.category.CategoryService;
import com.joaolucas.study.domain.exceptions.ObjectNotFoundException;
import com.joaolucas.study.infrastructure.database.category.CategoryEntity;
import com.joaolucas.study.infrastructure.database.product.ProductEntity;
import com.joaolucas.study.infrastructure.database.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;

    private final CategoryService categoryService;

    public ProductEntity find(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Object not found: " + id + ", Type: " + ProductEntity.class.getName()));
    }

    public Page<ProductEntity> search(String name, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        var pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<CategoryEntity> categoriaEntities = categoryService.findAllById(ids);
        return repo.findDistinctByNameAndCategoriesIn(name, categoriaEntities, pageRequest);
    }
}
