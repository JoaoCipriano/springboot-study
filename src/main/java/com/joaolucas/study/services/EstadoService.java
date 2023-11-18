package com.joaolucas.study.services;

import com.joaolucas.study.domain.Estado;
import com.joaolucas.study.repositories.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoService {

    private final EstadoRepository repository;

    public List<Estado> findAll() {
        return repository.findAllByOrderByNome();
    }
}
