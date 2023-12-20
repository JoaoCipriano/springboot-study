package com.joaolucas.study.infrastructure.database.product;

import com.joaolucas.study.infrastructure.database.category.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT product FROM ProductEntity product INNER JOIN product.categories cat WHERE product.name LIKE %:name% AND cat IN :categories")
    Page<ProductEntity> findDistinctByNameAndCategoriesIn(@Param("name") String name, @Param("categories") List<CategoryEntity> categories, Pageable pageRequest);
}
