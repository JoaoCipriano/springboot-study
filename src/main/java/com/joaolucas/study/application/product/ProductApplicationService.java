package com.joaolucas.study.application.product;

import com.joaolucas.study.application.product.mapper.ProductMapper;
import com.joaolucas.study.controller.product.model.ProductResponse;
import com.joaolucas.study.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductApplicationService {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductResponse find(Integer id) {
        return productMapper.toResponse(productService.find(id));
    }

    public Page<ProductResponse> search(String decodedName,
                                        List<Integer> ids,
                                        Integer page,
                                        Integer linesPerPage,
                                        String orderBy,
                                        String direction) {
        var pageableEntities = productService.search(decodedName, ids, page, linesPerPage, orderBy, direction);
        return productMapper.toPageableResponse(pageableEntities);
    }
}
