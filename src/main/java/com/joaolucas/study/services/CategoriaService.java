package com.joaolucas.study.services;

import com.joaolucas.study.domain.Categoria;
import com.joaolucas.study.dto.CategoriaDTO;
import com.joaolucas.study.repositories.CategoriaRepository;
import com.joaolucas.study.services.exceptions.DataIntegrityException;
import com.joaolucas.study.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repo;

    public Categoria find(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update(Categoria obj) {
        Categoria newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    private void updateData(Categoria newObj, Categoria obj) {
        newObj.setNome(obj.getNome());
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
        }
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        var pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public List<Categoria> findAll() {
        return repo.findAll();
    }

    public List<Categoria> findAllById(List<Integer> ids) {
        return repo.findAllById(ids);
    }

    public Categoria fromDTO(CategoriaDTO objDto) {
        return new Categoria(objDto.id(), objDto.nome());
    }
}
