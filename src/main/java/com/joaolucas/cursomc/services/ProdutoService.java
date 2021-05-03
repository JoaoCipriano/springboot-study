package com.joaolucas.cursomc.services;

import com.joaolucas.cursomc.domain.Categoria;
import com.joaolucas.cursomc.domain.Produto;
import com.joaolucas.cursomc.repositories.ProdutoRepository;
import com.joaolucas.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaService categoriaService;
	
	public Produto find(Integer id) {
		return repo.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(
						"Objeto não encontrado Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		var pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaService.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
