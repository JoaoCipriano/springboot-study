package com.joaolucas.cursomc.resources;

import com.joaolucas.cursomc.domain.Cidade;
import com.joaolucas.cursomc.domain.Estado;
import com.joaolucas.cursomc.dto.CidadeDTO;
import com.joaolucas.cursomc.dto.EstadoDTO;
import com.joaolucas.cursomc.services.CidadeService;
import com.joaolucas.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService service;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> findAll() {
        List<Estado> list = service.findAll();
        List<EstadoDTO> listDto = list.stream().map(EstadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(listDto);
    }

    @GetMapping(value = "/{estadoId}/cidades")
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
        List<Cidade> list = cidadeService.findByEstado(estadoId);
        List<CidadeDTO> listDto = list.stream().map(CidadeDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(listDto);
    }
}
