package com.joaolucas.study.domain.category;

import com.joaolucas.study.domain.exceptions.DataIntegrityException;
import com.joaolucas.study.domain.exceptions.ObjectNotFoundException;
import com.joaolucas.study.infrastructure.database.category.CategoryEntity;
import com.joaolucas.study.infrastructure.database.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryEntity find(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado Id: " + id + ", Tipo: " + CategoryEntity.class.getName()));
    }

    public CategoryEntity insert(CategoryEntity categoryEntity) {
        return repository.save(categoryEntity);
    }

    public void update(CategoryEntity categoryEntity, Integer id) {
        var currentCategoryEntity = find(id);
        updateData(currentCategoryEntity, categoryEntity);
        repository.save(currentCategoryEntity);
    }

    private void updateData(CategoryEntity currentCategoryEntity, CategoryEntity categoryEntity) {
        currentCategoryEntity.setName(categoryEntity.getName());
    }

    public void delete(Integer id) {
        find(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
        }
    }

    public Page<CategoryEntity> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        var pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }

    public List<CategoryEntity> findAll() {
        return repository.findAll();
    }

    public List<CategoryEntity> findAllById(List<Integer> ids) {
        return repository.findAllById(ids);
    }
}
