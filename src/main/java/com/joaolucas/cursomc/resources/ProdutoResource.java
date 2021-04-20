package com.joaolucas.cursomc.resources;

import com.joaolucas.cursomc.domain.Produto;
import com.joaolucas.cursomc.dto.ProdutoDTO;
import com.joaolucas.cursomc.resources.utils.URL;
import com.joaolucas.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;

	@GetMapping(value="/{id}")
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto obj = service.find(id);
		
		return ResponseEntity.ok().body(obj);
	}

	@GetMapping()
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		List<Integer> ids = URL.decodeIntList(categorias);
		String nomeDecoded = URL.decodeParam(nome);
		Page<ProdutoDTO> listDto = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction)
				.map(ProdutoDTO::new);
		return ResponseEntity.ok().body(listDto);
	}
	
}
