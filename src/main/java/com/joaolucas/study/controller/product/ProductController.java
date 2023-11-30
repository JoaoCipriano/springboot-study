package com.joaolucas.study.controller.product;

import com.joaolucas.study.infrastructure.database.product.ProductEntity;
import com.joaolucas.study.domain.product.Product;
import com.joaolucas.study.controller.utils.URL;
import com.joaolucas.study.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductEntity> find(@PathVariable Integer id) {
        ProductEntity obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }

    @GetMapping()
    public ResponseEntity<Page<Product>> findPage(
            @RequestParam(value = "name", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        List<Integer> ids = URL.decodeIntList(categorias);
        String nomeDecoded = URL.decodeParam(nome);
        Page<Product> listDto = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction)
                .map(Product::new);
        return ResponseEntity.ok().body(listDto);
    }

}
