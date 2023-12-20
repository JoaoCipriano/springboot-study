package com.joaolucas.study.controller.product;

import com.joaolucas.study.application.product.ProductApplicationService;
import com.joaolucas.study.controller.product.model.ProductResponse;
import com.joaolucas.study.controller.utils.URL;
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
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductApplicationService productApplicationService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> find(@PathVariable Integer id) {
        return ResponseEntity.ok(productApplicationService.find(id));
    }

    @GetMapping()
    public ResponseEntity<Page<ProductResponse>> findPage(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "categories", defaultValue = "") String categories,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        List<Integer> ids = URL.decodeIntList(categories);
        String decodedName = URL.decodeParam(name);
        var pageableResponse = productApplicationService.search(decodedName, ids, page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok(pageableResponse);
    }
}
