package com.joaolucas.study.controller.order;

import com.joaolucas.study.application.order.OrderApplicationService;
import com.joaolucas.study.controller.order.model.OrderRequest;
import com.joaolucas.study.controller.order.model.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderResponse> find(@PathVariable Integer id) {
        return ResponseEntity.ok(orderApplicationService.find(id));
    }

    @GetMapping()
    public ResponseEntity<Page<OrderResponse>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "instant") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        Page<OrderResponse> list = orderApplicationService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody OrderRequest orderRequest) {
        var response = orderApplicationService.insert(orderRequest);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
