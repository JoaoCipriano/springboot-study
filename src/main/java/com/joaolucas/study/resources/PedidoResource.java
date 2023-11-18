package com.joaolucas.study.resources;

import com.joaolucas.study.domain.Pedido;
import com.joaolucas.study.dto.PedidoNewDTO;
import com.joaolucas.study.services.PedidoService;
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
@RequestMapping(value = "/pedidos")
@RequiredArgsConstructor
public class PedidoResource {

    private final PedidoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pedido> find(@PathVariable Integer id) {
        Pedido obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }

    @GetMapping()
    public ResponseEntity<Page<Pedido>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        Page<Pedido> list = service.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody PedidoNewDTO objDto) {
        Pedido obj = service.fromDTO(objDto);
        service.insert(obj);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
