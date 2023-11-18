package com.joaolucas.study.services;

import com.joaolucas.study.domain.Categoria;
import com.joaolucas.study.domain.Produto;
import com.joaolucas.study.repositories.ProdutoRepository;
import com.joaolucas.study.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repo;

    private final CategoriaService categoriaService;

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
