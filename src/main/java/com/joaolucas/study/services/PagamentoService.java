package com.joaolucas.study.services;

import com.joaolucas.study.domain.Pagamento;
import com.joaolucas.study.repositories.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository repo;

    public Pagamento insert(Pagamento pagamento) {
        return repo.save(pagamento);
    }
}
