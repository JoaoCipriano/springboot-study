package com.joaolucas.study.services;

import com.joaolucas.study.domain.Cidade;
import com.joaolucas.study.repositories.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CidadeService {

    private final CidadeRepository repository;

    public List<Cidade> findByEstado(Integer estadoId) {
       return  repository.findCidades(estadoId);
    }
}
