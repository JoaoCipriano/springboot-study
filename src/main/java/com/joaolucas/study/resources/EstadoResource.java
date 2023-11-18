package com.joaolucas.study.resources;

import com.joaolucas.study.domain.Cidade;
import com.joaolucas.study.domain.Estado;
import com.joaolucas.study.dto.CidadeDTO;
import com.joaolucas.study.dto.EstadoDTO;
import com.joaolucas.study.services.CidadeService;
import com.joaolucas.study.services.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/estados")
@RequiredArgsConstructor
public class EstadoResource {

    private final EstadoService service;

    private final CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<Estado> list = service.findAll();
        List<EstadoDTO> listDto = list.stream().map(EstadoDTO::new).toList();
        return ResponseEntity.ok(listDto);
    }

    @GetMapping(value = "/{estadoId}/cidades")
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
        List<Cidade> list = cidadeService.findByEstado(estadoId);
        List<CidadeDTO> listDto = list.stream().map(CidadeDTO::new).toList();
        return ResponseEntity.ok(listDto);
    }
}
